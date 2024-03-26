package edu.lehigh.cse216.group17.backend;

/**
 * UserDataRequest create a simple way to store user data
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class UserDataRequest {
    /**
     * The uername of the user
     */
    public String mUsername;

    /**
     * The email of the user
     */
    public String mEmail;

    /**
     * The gender identity
     */
    public String mGenderIdentity;

    /**
     * The sexual orientation of the user
     */
    public String mSexualOrientation;

    /**
     * The sexual orientation of the user
     */
    public String mNote;

    /**
    * Creating a contructor to create a user data request
    * @param username username
    * @param email email address
    * @param Gender gender identity
    * @param SexualOrientation SO
    * @param note user bio
    */
    public UserDataRequest(String username, String email, String Gender, String SexualOrientation, String note){
        mUsername = username;
        mEmail = email;
        mGenderIdentity = Gender;
        mSexualOrientation = SexualOrientation;
        mNote=note;
    }
}
