#include <cassert>
#include <cstdio>
#include <cstring>
#include <functional>
#include <iostream>
#include <memory>
#include <openssl/rand.h>
#include <openssl/sha.h>
#include <string>
#include <sys/wait.h>
#include <unistd.h>
#include <utility>
#include <vector>

#include "../common/contextmanager.h"
#include "../common/err.h"
#include "../common/protocol.h"

#include "authtableentry.h"
#include "format.h"
#include "helpers.h"
#include "map.h"
#include "map_factories.h"
#include "mru.h"
#include "persist.h"
#include "quotas.h"
#include "storage.h"

using namespace std;

/// MyStorage is the student implementation of the Storage class
class MyStorage : public Storage {

  //! Check Quota Helper Method
  /// Checks if the operation by the user exceeds the quotas.
  ///
  /// @param user   The user's name
  /// @param dataSize The size of the data upload/download
  /// @param checkUpload  bool flag to check upload quota
  /// @param checkDownload  bool flag to check download quota
  /// @param CheckRequest  bool flag to check request quota
  result_t checkQuota(const string& user, size_t dataSize, bool checkUpload, bool checkDownload, bool checkRequest) {
    
    bool upload_quota_exceeded = false; //bool tracks if upload quotas exceed
    bool download_quota_exceeded = false; //bool tracks if download quotas exceed
    bool request_quota_exceeded = false; //bool tracks if request quotas exceed

    quota_table->do_with_readonly(user, [&](Quotas* user_quotas) {  //accesses quota for specific user using do_with_readonly from map and checks each quota
        if (user_quotas != nullptr) {
            if (checkRequest && !user_quotas->requests->check_add(1)) {  //if request quota is exceeded
                request_quota_exceeded = true; //! quota exceeded yes
            }
            else if (checkUpload && !user_quotas->uploads->check_add(dataSize)) {  //if upload quota is exceeded
                upload_quota_exceeded = true; //! quota exceeded yes
            } else if (checkDownload && !user_quotas->downloads->check_add(dataSize)) { //if download quota is exceeded
                download_quota_exceeded = true; //! quota exceeded yes
            }// else if (checkRequest && !user_quotas->requests->check_add(1)) {  //if request quota is exceeded
            //     request_quota_exceeded = true; //! quota exceeded yes
            // }
        }
    }, [](){}); 

    if (upload_quota_exceeded) {
        return {false, RES_ERR_QUOTA_UP, {}};   //if upload quota was exceeded 
    }
    if (download_quota_exceeded) {
        return {false, RES_ERR_QUOTA_DOWN, {}};   //if download quota was exceeded 
    }
    if (request_quota_exceeded) {
        return {false, RES_ERR_QUOTA_REQ, {}};   //if request quota was exceeded 
    }

    bool userExists = quota_table->do_with_readonly(user, [](Quotas*){}, [](){}); //checks if user quotas are in quota table if not then it needs to be made
    if (!userExists) {
        // user's quotas don't exis make them
        Quotas* newQuotas = new Quotas{
          //quota_factory is used to generate quota trackers for upload, download, request quotas (amount, duration)
            quota_factory(up_quota, quota_dur),
            quota_factory(down_quota, quota_dur),
            quota_factory(req_quota, quota_dur)
        };
        quota_table->insert(user, newQuotas, [](){});
        return checkQuota(user, dataSize, checkUpload, checkDownload, checkRequest);  //quota check on new created quotas
    }

    return {true, RES_OK, {}};  //if it's not exceeded return success
};
  /// The map of authentication information, indexed by username
  Map<string, AuthTableEntry> *auth_table;

  /// The map of key/value pairs
  Map<string, vector<uint8_t>> *kv_store;

  /// The name of the file from which the Storage object was loaded, and to
  /// which we persist the Storage object every time it changes
  string filename = "";

  /// The open file
  FILE *storage_file = nullptr;

  /// The upload quota
  const size_t up_quota;

  /// The download quota
  const size_t down_quota;

  /// The requests quota
  const size_t req_quota;

  /// The number of seconds over which quotas are enforced
  const double quota_dur;

  /// The table for tracking the most recently used keys
  mru_manager *mru;

  /// A table for tracking quotas
  Map<string, Quotas *> *quota_table;

public:
  /// Construct an empty object and specify the file from which it should be
  /// loaded.  To avoid exceptions and errors in the constructor, the act of
  /// loading data is separate from construction.
  ///
  /// @param fname   The name of the file to use for persistence
  /// @param buckets The number of buckets in the hash table
  /// @param upq     The upload quota
  /// @param dnq     The download quota
  /// @param rqq     The request quota
  /// @param qd      The quota duration
  /// @param top     The size of the "top keys" cache
  /// @param admin   The administrator's username
  MyStorage(const std::string &fname, size_t buckets, size_t upq, size_t dnq,
            size_t rqq, double qd, size_t top, const std::string &)
      : auth_table(authtable_factory(buckets)),
        kv_store(kvstore_factory(buckets)), filename(fname), up_quota(upq),
        down_quota(dnq), req_quota(rqq), quota_dur(qd), mru(mru_factory(top)),
        quota_table(quotatable_factory(buckets)) {}
  //!jonah is da best
  /// Destructor for the storage object.
  virtual ~MyStorage() {
    // TODO: you probably want to free some memory here...
  }

  /// Create a new entry in the Auth table.  If the user already exists, return
  /// an error.  Otherwise, create a salt, hash the password, and then save an
  /// entry with the username, salt, hashed password, and a zero-byte content.
  ///
  /// @param user The user name to register
  /// @param pass The password to associate with that user name
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t add_user(const string &user, const string &pass) {
    // NB: the helper (.o provided) does all the work for this operation :)
    return add_user_helper(user, pass, auth_table, storage_file);
  }

  /// change the password, but do so if and only if the old password
  /// matches
  ///
  /// @param user    The name of the user whose content is being set
  /// @param pass    The password for the user, used to authenticate
  /// @param newpasss The new password for this user
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t chg_user_pass(const string &user, const string &pass,
                                 const string &newpass) {
    return chg_user_pass_helper(user, pass, newpass, auth_table, storage_file);
  }

  /// Set the data bytes for a user, but do so if and only if the password
  /// matches
  ///
  /// @param user    The name of the user whose content is being set
  /// @param pass    The password for the user, used to authenticate
  /// @param content The data to set for this user
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t set_user_data(const string &user, const string &pass,
                                 const vector<uint8_t> &content) {
    // NB: the helper (.o provided) does all the work for this operation :)
    return set_user_data_helper(user, pass, content, auth_table, storage_file);
  }

  /// Return a copy of the user data for a user, but do so only if the password
  /// matches
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  /// @param who  The name of the user whose content is being fetched
  ///
  /// @return A result tuple, as described in storage.h.  Note that "no data" is
  ///         an error
  virtual result_t get_user_data(const string &user, const string &pass,
                                 const string &who) {
    // NB: the helper (.o provided) does all the work for this operation :)
    return get_user_data_helper(user, pass, who, auth_table);
  }

  /// Return a newline-delimited string containing all of the usernames in the
  /// auth table
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t get_all_users(const string &user, const string &pass) {
    // NB: the helper (.o provided) does all the work for this operation :)
    return get_all_users_helper(user, pass, auth_table);
  }

  /// Authenticate a user
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t auth(const string &user, const string &pass) {
    // NB: the helper (.o provided) does all the work for this operation :)
    return auth_helper(user, pass, auth_table);
  }

  /// Create a new key/value mapping in the table
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  /// @param key  The key whose mapping is being created
  /// @param val  The value to copy into the map
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t kv_insert(const string &user, const string &pass,
                             const string &key, const vector<uint8_t> &val) {
    //! Step 1: Authenticate User
    auto auth_result = auth(user, pass);
    if (!auth_result.succeeded) {
      return {false, RES_ERR_LOGIN, {}};
    }
    //! Step 2: check upload quota
    auto quota_result = checkQuota(user, val.size(), true, false, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }

    //! Step 3: insert k/v pair
    bool insert_success = kv_store->insert(key, val, [this, &key]() {
        // Optional: code to execute on successful insert
        mru->insert(key); // Update the MRU cache
    });

    if (!insert_success) {
        return {false, RES_ERR_KEY, {}}; // Key already exists
    }
    log_sv(storage_file, KVENTRY, key, val);
    //! Step 4: return success
    return {true, RES_OK, {}};

    //TODO: NB: log_sv() in persist.h (implementation in persist.o) will be helpful
    //     here
  };

  /// Get a copy of the value to which a key is mapped
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  /// @param key  The key whose value is being fetched
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t kv_get(const string &user, const string &pass,
                          const string &key) {       
    //! Step 1: Authenticate User
    auto auth_result = auth(user, pass);
    if (!auth_result.succeeded) {
      return {false, RES_ERR_LOGIN, {}};
    }

    //! Step 3: Retrieve the value associated with the key from the KV store
    std::vector<uint8_t> value;
    bool key_found = kv_store->do_with_readonly(
        key,
        [&value](const std::vector<uint8_t> &existingVal) { // if key is found in kv_store capture value by reference
          value = existingVal;
          return true;
        }, [] { return false; }); // if key is not found in kv_store

    //! Step 2: check upload quota
    auto quota_result = checkQuota(user, value.size(), false, true, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }

    if (!key_found)
      return {false, RES_ERR_KEY, {}};

    mru->insert(key);
    //! Step 4: return success
    return {true, RES_OK, value};
  };

  /// Delete a key/value mapping
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  /// @param key  The key whose value is being deleted
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t kv_delete(const string &user, const string &pass,
                             const string &key) {
    //! Step 1: Authenticate User
    auto auth_result = auth(user, pass);
    if (!auth_result.succeeded) {
      return {false, RES_ERR_LOGIN, {}};
    }

    //! Step 2: check upload quota
    auto quota_result = checkQuota(user, 1, false, false, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }

    bool key_found = kv_store->remove(key,[] {});
    mru->remove(key);
    if (!key_found)
      return {false, RES_ERR_KEY, {}}; //??
    log_s(storage_file, KVDELETE, key);
    //! Step 3: return success
    return {true, RES_OK, {}};
  };

  /// Insert or update, so that the given key is mapped to the give value
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  /// @param key  The key whose mapping is being upserted
  /// @param val  The value to copy into the map
  ///
  /// @return A result tuple, as described in storage.h.  Note that there are
  /// two
  ///         "OK" messages, depending on whether we get an insert or an update.
  virtual result_t kv_upsert(const string &user, const string &pass,
                             const string &key, const vector<uint8_t> &val) {
    //! Step 1: Authenticate User
    auto auth_result = auth(user, pass);
    if (!auth_result.succeeded) {
      return {false, RES_ERR_LOGIN, {}};
    }

    //! Step 2: check upload quota
    auto quota_result = checkQuota(user, val.size(), true, false, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }

    //! Step 3: insert k/v pair
    bool upsert_success = kv_store->upsert(key, val,
        [this, &key]() {
            //if the upsert is insert
            mru->insert(key); // Update the MRU cache
        },
        [this, &key]() {
            // if the upsert is update
            mru->insert(key); // Update the MRU cache
        }
    );
    //! Step 4: return success
    if (upsert_success) {
        log_sv(storage_file, KVENTRY, key, val);
        return {true, RES_OKINS, {}}; // Insertion success
    } else {
        log_sv(storage_file, KVUPDATE, key, val);
        return {true, RES_OKUPD, {}}; // Update success
    }
  };

  /// Return all of the keys in the kv_store, as a "\n"-delimited string
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t kv_all(const string &user, const string &pass) {
    // Step 1: Authenticate the user
    result_t auth_result = auth(user, pass);
    if (!auth_result.succeeded)
      return {false, RES_ERR_LOGIN, {}};
    // Step 2: Retrieve all keys from the KV store
    std::vector<string> keys;
    // To suppress unused variable warning if val is not used
    kv_store->do_all_readonly([&](const string &key, const std::vector<uint8_t> &val) {keys.push_back(key);
                                                                                      (void)val;}, [] {});
    // If keys are empty return false
    if (keys.empty())
      return {false, RES_ERR_NO_DATA, {}};


    //! Step 2: check upload quota
    auto quota_result = checkQuota(user, keys.size(), false, true, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }
    // Step 3: Concatenate all keys into a "\n"-delimited string using a loop
    string all_keys;
    for (size_t i = 0; i < keys.size(); ++i){
      all_keys += keys[i];
      if (i < keys.size() - 1) // don't add a newline after the last key
        all_keys += '\n';
    }
    // Step 4: Convert the string to a vector<uint8_t> for the result
    std::vector<uint8_t> data(all_keys.begin(), all_keys.end());
    // Step 5: Return the result
    return {true, RES_OK, data};
  };

  /// Return all of the keys in the kv_store's MRU cache, as a "\n"-delimited
  /// string
  ///
  /// @param user The name of the user who made the request
  /// @param pass The password for the user, used to authenticate
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t kv_top(const string &user, const string &pass) {
   //! Step 1: Authenticate the user
    result_t auth_result = auth(user, pass);
    if (!auth_result.succeeded)
      return {false, RES_ERR_LOGIN, {}};
      
    //! Step 2: Retrieve all keys from the KV store
    std::string top_keys = mru->get();
    if(top_keys.empty()){
      return {false, RES_ERR_NO_DATA, {}};
    }
    int list_length = 1; // Start with 1 assuming there's at least one element

    for (char ch : top_keys) {
        if (ch == '\n') {
            list_length++;
        }
    }
    //! Step 3: check download quota
    auto quota_result = checkQuota(user, list_length, false, true, true);
    if (!quota_result.succeeded) {
        return quota_result; // Quota exceeded
    }
    //! Step 4: Convert the string to a vector<uint8_t> for the result
    std::vector<uint8_t> data(top_keys.begin(), top_keys.end());
    //! Step 5: Return the result
    return {true, RES_OK, data};
  };

  /// Shut down the storage when the server stops.  This method needs to close
  /// any open files related to incremental persistence.  It also needs to clean
  /// up any state related to .so files.  This is only called when all threads
  /// have stopped accessing the Storage object.
  virtual void shutdown() {
    // NB: Based on how the other methods are implemented in the helper file, we
    //     need this command here:
    fclose(storage_file);
  }

  /// Write the entire Storage object to the file specified by this.filename. To
  /// ensure durability, Storage must be persisted in two steps.  First, it must
  /// be written to a temporary file (this.filename.tmp).  Then the temporary
  /// file can be renamed to replace the older version of the Storage object.
  ///
  /// @return A result tuple, as described in storage.h
  virtual result_t save_file() {
    // NB: the helper (.o provided) does all the work for this operation :)
    return save_file_helper(auth_table, kv_store, filename, storage_file);
  }

  /// Populate the Storage object by loading this.filename.  Note that load()
  /// begins by clearing the maps, so that when the call is complete, exactly
  /// and only the contents of the file are in the Storage object.
  ///
  /// @return A result tuple, as described in storage.h.  Note that a
  /// non-existent
  ///         file is not an error.
  virtual result_t load_file() {
    // NB: the helper (.o provided) does all the work from p1/p2/p3 for this
    //     operation.  Depending on how you choose to implement quotas, you may
    //     need to edit this.
    return load_file_helper(auth_table, kv_store, filename, storage_file);
  };
};

/// Create an empty Storage object and specify the file from which it should
/// be loaded.  To avoid exceptions and errors in the constructor, the act of
/// loading data is separate from construction.
///
/// @param fname   The name of the file to use for persistence
/// @param buckets The number of buckets in the hash table
/// @param upq     The upload quota
/// @param dnq     The download quota
/// @param rqq     The request quota
/// @param qd      The quota duration
/// @param top     The size of the "top keys" cache
/// @param admin   The administrator's username
Storage *storage_factory(const std::string &fname, size_t buckets, size_t upq,
                         size_t dnq, size_t rqq, double qd, size_t top,
                         const std::string &admin) {
  return new MyStorage(fname, buckets, upq, dnq, rqq, qd, top, admin);
}
