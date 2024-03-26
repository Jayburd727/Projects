package edu.lehigh.cse216.group17.backend;

/**
 * UserDataRequest create a simple way to store user data
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class VotesRequest {
    /**
     * The post-id
     */
    public int mPostID;

    /**
     * The vote count
     */
    public int mVote;

    /**
     * The user's username
     */
    public String mUsername;
}
