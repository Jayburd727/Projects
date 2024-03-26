#include <deque>
#include <iostream>
#include <mutex>
#include <unordered_set> //help deal with duplicates
#include "mru.h"

using namespace std;

/// my_mru maintains a listing of the K most recent elements that have been
/// given to it.  It can be used to produce a "top" listing of the most recently
/// accessed keys.
class my_mru : public mru_manager {
size_t threshold; //Making threshold value
deque<string>queue;

public:
  std::mutex mtx; //thread safety

  /// Construct the mru_manager by specifying how many things it should track
  ///
  /// @param elements The number of elements that can be tracked -> Threshold (T)
  my_mru(size_t elements) : threshold(elements) {}

  /// Destruct the mru_manager
  virtual ~my_mru() {}

  /// Insert an element into the mru_manager, making sure that (a) there are no
  /// duplicates, and (b) the manager holds no more than /max_size/ elements.
  ///
  /// @param elt The element to insert
  virtual void insert(const std::string &elt) {
    std::lock_guard<std::mutex> lock(mtx);
    for (auto it = queue.begin(); it != queue.end(); ++it) {  //begin element; begin element not equal to last element; increment by 1
        if (*it == elt) {
            queue.erase(it);
            break; // found and removed dup 
        }
    }

    queue.push_front(elt); //insert element in front of the queue

    // If the queue size exceeds the threshold lets remove the oldest element
    if (queue.size() > threshold) {
        queue.pop_back();
    }

    // cout << "my_mru.cc::insert() is not implemented\n";
  }

  /// Remove an instance of an element from the mru_manager.  This can leave the
  /// manager in a state where it has fewer than max_size elements in it.
  ///
  /// @param elt The element to remove
  virtual void remove(const std::string &elt) {
    std::lock_guard<std::mutex> lock(mtx);
        for (auto it = queue.begin(); it != queue.end(); ++it) {
        if (*it == elt) {
            queue.erase(it);
            break; // element removed and we exit loop 
        }
    }
    // cout << "my_mru.cc::remove() is not implemented\n";
  }

  /// Clear the mru_manager
  virtual void clear() { 
    std::lock_guard<std::mutex> lock(mtx);
    queue.clear();  //cleared
    // cout << "my_mru.cc::clear() is not implemented\n"; 
    }

  /// Produce a concatenation of the top entries, in order of popularity
  ///
  /// @return A newline-separated list of values
  virtual std::string get() { 
    std::lock_guard<std::mutex> lock(mtx);
    std::string result;
    for (const auto& elt : queue) {
        if (!result.empty()) {
            result += "\n";  // Add newline before each element, except first elt
        }
        result += elt;
    }
    return result;
    // cout << "my_mru.cc::get() is not implemented\n"; 
    }
};

/// Construct the mru_manager by specifying how many things it should track
///
/// @param elements The number of elements that can be tracked in MRU fashion
///
/// @return An mru manager object
mru_manager *mru_factory(size_t elements) { 
  return new my_mru(elements); 
  }