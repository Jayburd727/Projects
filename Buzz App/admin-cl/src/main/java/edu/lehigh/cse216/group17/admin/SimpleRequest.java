package edu.lehigh.cse216.group17.admin;

/**
 * SimpleRequest provides a format for clients to present title and message 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequest {
    /**
     * The title being provided by the client.
     */
    public String mTitle;

    /**
     * The content message being provided by the client.
     */
    public String mContent;

    /**
     * The username of the User
     */
    public String mUser;

    /**
     * The token ID from Google OAuth
     */
    public String mToken;
}