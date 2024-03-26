// http://www.cplusplus.com/reference/ctime/time/ is helpful here
#include <deque>
#include <iostream>
#include <memory>
#include <ctime>
#include <mutex>

#include "quota_tracker.h"

using namespace std;

/// quota_tracker stores time-ordered information about events.  It can count
/// events within a pre-set, fixed time threshold, to decide if a new event can
/// be allowed without violating a quota.
class my_quota_tracker : public quota_tracker {
//deque with a pair -> double and size_t
std::deque<std::pair<time_t, size_t>> quotaDeque;
//max size
size_t max_size;
//duration
double timer_duration;

public:
  std::mutex mtx; //thread safety
  /// Construct a tracker that limits usage to quota_amount per quota_duration
  /// seconds
  ///
  /// @param amount   The maximum amount of service
  /// @param duration The time over which the service maximum can be spread out
  my_quota_tracker(size_t amount, double duration)
    :  max_size(amount), timer_duration(duration) {
    }

  /// Destruct a quota tracker
  virtual ~my_quota_tracker() {}

  /// Decide if a new event is permitted, and if so, add it.  The attempt is
  /// allowed if it could be added to events, while ensuring that the sum of
  /// amounts for all events within the duration is less than q_amnt.
  ///
  /// @param amount The amount of the new request
  ///
  /// @return false if the amount could not be added without violating the
  ///         quota, true if the amount was added while preserving the quota
  virtual bool check_add(size_t amount) {
    std::lock_guard<std::mutex> lock(mtx);
    //check all the current elements in the deque to see if any have expired
    size_t current_deque_size = 0;
    for(auto element = quotaDeque.begin(); element != quotaDeque.end(); element++){
      time_t timer = std::time(nullptr);
      double afterTime = difftime(timer, element->first);
      if(afterTime > 0){
        quotaDeque.pop_front();
      }
      else{
        current_deque_size += element->second;
      }
    }
    if((current_deque_size + amount) > max_size){
      return false;
    }
    //add new stuff
    time_t currentTime = std::time(nullptr);
    currentTime += static_cast<time_t>(timer_duration);
    quotaDeque.push_back({currentTime, amount});
    return true;
  }
};

/// Construct a tracker that limits usage to quota_amount per quota_duration
/// seconds
///
/// @param amount   The maximum amount of service
/// @param duration The time over which the service maximum can be spread out
quota_tracker *quota_factory(size_t amount, double duration) {
  return new my_quota_tracker(amount, duration);
}