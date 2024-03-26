package edu.lehigh.cse216.group17.backend;
/**
 * Creating a User info class
 */
public class UserInfo {
    /**
     * The email of the user
     */
    public String mUSER_ID;

    /**
     * The session key
     */
    public Integer mSessionKey;

    /**
     * The verification of user ID
     */
    public String mVerUSER_ID;

    /**
     * The verification of session ID
     */
    public String mVerSessionKey;

    /**
    * Creating a constructor for user info 
    * @param USER_ID user ID
    * @param SessionKey session key
    * @param verUSER_ID string if  user is verifies
    * @param VerSessionKey string if session key is verified
    */
    public UserInfo(String USER_ID, Integer SessionKey, String verUSER_ID, String VerSessionKey){
        mUSER_ID = USER_ID;
        mSessionKey = SessionKey;
        mVerUSER_ID = verUSER_ID;
        mVerSessionKey = VerSessionKey;
    }

}
