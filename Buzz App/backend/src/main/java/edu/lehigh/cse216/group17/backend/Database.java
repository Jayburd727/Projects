package edu.lehigh.cse216.group17.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

import edu.lehigh.cse216.group17.backend.PostData;
import edu.lehigh.cse216.group17.backend.CommentData;
import edu.lehigh.cse216.group17.backend.VotesRequest;
import edu.lehigh.cse216.group17.backend.UserDataRequest;
import edu.lehigh.cse216.group17.backend.SimpleRequest;
import edu.lehigh.cse216.group17.backend.StructuredResponse;
import com.google.api.services.drive.model.File;


/**
 * Creating a database class
 */
public class Database {
    
    /***********************************************************************************************
    * PREPARED STATEMENTS DECLARATION SECTION
    ***********************************************************************************************/

    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all posts or messages
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting one post or message from the database
     * based on the message ID
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for getting the post based on Title
     */
    private PreparedStatement mSelectOnebyTitle;


    /**
     * A prepared statement for inserting one post or message from the database
     * based on the message ID
     */
    private PreparedStatement mInsertOne;

    /**
     * A prepared statement for updating one post or message from the database
     * based on the message ID
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for deleting deleting one post or message from the database
     * based on the message ID
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement to get number of likes of a post based on
     * its ID
     */
    private PreparedStatement mGetLikes;

    /**
     * A prepared statement for updating a user's GI
     */
    private PreparedStatement mUpdateUserGenderIdentity;

    /**
     * A prepared statement for updating a user's Sexual Orientation
     */
    private PreparedStatement mUpdateUserSexualOrientation;

    /**
     * A prepared statement for updating a user's Note
     */
    private PreparedStatement mUpdateUserNote;

    /**
     * A prepared statement for updating up votes
     */
    private PreparedStatement mUpdateUpVotes;

    /**
     * A prepared statement for updating down votes
     */
    private PreparedStatement mUpdateDownVotes;

    /**
     * A prepared statement for inserting the action
     */
    private PreparedStatement mInsertAction;

    /**
     * A prepared statement for inserting a comment
     */
    private PreparedStatement mInsertComment;

    /**
     * A prepared statement for inserting a user
     */
    private PreparedStatement mInsertUser;

    /**
     * A prepared statement for updating a comment
     */
    private PreparedStatement mUpdateComment;

    /**
     * A prepared statement for selecting a comment by the comment ID
     */
    private PreparedStatement mSelectCommentByID;

    /**
     * A prepared statement for selecting a comment by the user's username
     */
    private PreparedStatement mSelectCommentByUsername;

    /**
     * A prepared statement for selecting a comment by post
     */
    private PreparedStatement mSelectCommentByPost;
    

    /**
     * A prepared statement for selecting the number of up votes by the post ID
     */
    private PreparedStatement mSelectUpVotesByID;


    /**
     * A prepared statement for selecting the number of down votes by the post ID
     */
    private PreparedStatement mSelectDownVotesByID;

    /**
     * A prepared statement for selecting the most recent vote made by a user on a post
     */
    private PreparedStatement mSelectMostRecentVote;

    /**
     * A prepared statement to check for user
     */
    private PreparedStatement mCheckUser;

    //phase 3
    private PreparedStatement mSelectLinkById;
    private PreparedStatement mSelectOneById;
    private PreparedStatement mDeleteById;
    private PreparedStatement mInsertFile;




    /***********************************************************************************************
    * DATABASE CONFIGURATION AND CONNECTION MANAGEMENT SECTION
    ***********************************************************************************************/

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */

    private Database() {
    }


    /**
     * Get a fully-configured connection to the database
     * 
     * @param url The URL of the database
     * @return A Database object, or null if we cannot connect properly
     */

    static Database getDatabase(String ip, String port, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);

            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        //Setup the Prepared Statements
        try {
            //CRUD Operations
            db.mSelectAll=db.mConnection.prepareStatement("SELECT * FROM POST");
            db.mSelectOne=db.mConnection.prepareStatement("SELECT * FROM POST WHERE ID=?");
            db.mSelectOnebyTitle=db.mConnection.prepareStatement("SELECT * FROM POST WHERE TITLE=?");
            db.mInsertOne=db.mConnection.prepareStatement("SET timezone = 'America/New_York';"+
                                                        "INSERT INTO POST VALUES (default,?,?,?,default,default,default)");
            db.mInsertUser=db.mConnection.prepareStatement("INSERT INTO USERDATA VALUES (?,?,?,?,?)");
            db.mUpdateUserGenderIdentity=db.mConnection.prepareStatement("UPDATE USERDATA SET GENDERIDENTITY = ? WHERE USERNAME = ?");
            db.mUpdateUserSexualOrientation=db.mConnection.prepareStatement("UPDATE USERDATA SET SEXUALORIENTATION = ? WHERE USERNAME = ?");
            db.mUpdateUserNote=db.mConnection.prepareStatement("UPDATE USERDATA SET NOTE = ? WHERE USERNAME = ?");
            db.mUpdateOne=db.mConnection.prepareStatement("UPDATE POST SET TITLE = ?, CONTENT=? WHERE id = ?");
            db.mDeleteOne=db.mConnection.prepareStatement("DELETE FROM POST WHERE ID = ?");
            db.mUpdateUpVotes=db.mConnection.prepareStatement("UPDATE POST SET UPVOTE = ? WHERE id = ?");
            db.mUpdateDownVotes=db.mConnection.prepareStatement("UPDATE POST SET DOWNVOTE = ? WHERE id = ?");
            db.mInsertAction=db.mConnection.prepareStatement("SET timezone = 'America/New_York';"+
                                                            "INSERT INTO ACTION(P_ID, Username, Type) VALUES (?,?,?)");
            db.mInsertComment=db.mConnection.prepareStatement("SET timezone='America/New_York';INSERT INTO COMMENT(P_ID, Username, Content) VALUES (?,?,?);");
            db.mUpdateComment=db.mConnection.prepareStatement("UPDATE COMMENT SET CONTENT=? WHERE id = ?");
            db.mSelectCommentByID=db.mConnection.prepareStatement("SELECT CONTENT FROM COMMENT WHERE ID=?");
            db.mSelectCommentByUsername=db.mConnection.prepareStatement("SELECT CONTENT FROM COMMENT WHERE USERNAME=?");
            db.mSelectCommentByPost=db.mConnection.prepareStatement("SELECT CONTENT FROM COMMENT WHERE P_ID=?");
            db.mSelectUpVotesByID=db.mConnection.prepareStatement("SELECT UPVOTE FROM POST WHERE ID=?");
            db.mSelectDownVotesByID=db.mConnection.prepareStatement("SELECT DOWNVOTE FROM POST WHERE ID=?");
            db.mSelectMostRecentVote=db.mConnection.prepareStatement("SELECT type from Action where username=? and p_id=? order by tms desc limit 1");
            db.mCheckUser=db.mConnection.prepareStatement("SELECT count (*) as c from USERDATA where username=?");

        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }

        return db;
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /***********************************************************************************************
    * DATABASE DATA OPERATIONS
    ***********************************************************************************************/

    /**
     * Select ALL Posts from database
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<PostData> selectAll(){
        ArrayList<PostData> res = new ArrayList<PostData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new PostData(rs.getInt("ID"), rs.getString("Title"), 
                rs.getString("Content"),rs.getTimestamp("Created"),rs.getInt("Upvote"), rs.getInt("Downvote"), rs.getString("Username")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the data of One Post based on its ID
     * 
     * @param P_ID the ID of the post
     * @return one post row of data
     */

     PostData selectOne(int P_ID){
        PostData res = null;
        try {
            mSelectOne.setInt(1, P_ID);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new PostData(rs.getInt("ID"), rs.getString("Title"), 
                rs.getString("Content"),rs.getTimestamp("Created"),rs.getInt("Upvote"),rs.getInt("Downvote"), rs.getString("Username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get the data of One Post based on the Post Title
     * 
     * @param title The title of the Post
    **/
    PostData selectByTitle(String title){
        PostData res = null;
        try {
            mSelectOnebyTitle.setString(1, title);
            ResultSet rs = mSelectOnebyTitle.executeQuery();
            if (rs.next()) {
                res = new PostData(rs.getInt("ID"), rs.getString("Title"), 
                rs.getString("Content"),rs.getTimestamp("Created"),rs.getInt("Upvote"),rs.getInt("Downvote"), rs.getString("Username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Insert a post into the database
     * 
     * @param username username of the user making the post
     * @param title The post title
     * @param content The post content
     * 
     * @return The number of rows that were inserted
     */
    int insertRow(String username, String title, String content) {
        int count = 0;
        try {
            mInsertOne.setString(2, title);
            mInsertOne.setString(3, content);
            mInsertOne.setString(1, username);
            mInsertOne.executeUpdate();
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Update the post for a row in the database
     * 
     * @param title The post title
     * @param content The post content
     * 
     * @return The number of rows that were inserted. -1 if there was an error
    */
    int updateOne(int id, String title, String content) {
        int res = -1;
        try {
            mUpdateOne.setString(1, title);
            mUpdateOne.setString(2, content);
            mUpdateOne.setInt(3, id);
            res = mUpdateOne.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * Delete a post row by ID
     * 
     * @param P_ID The id of the post to delete
     * 
     * @return The number of posts that were deleted.  -1 indicates an error.
     */
    int deleteOne(int P_ID) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, P_ID);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Inserts a User into USERDATA
     * 
     * @param Username The user's username
     * @param Email The User's email address
     * @param GenderIdentity the user's gender identity
     * @param SexualOrientation the user's sexual orientation
     * @param Note the user's note
     */
    int insertUser(String Username, String Email, String GenderIdentity, String SexualOrientation, String Note){
        int count =0;
        try {
            mInsertUser.setString(1, Username);
            mInsertUser.setString(2, Email);
            mInsertUser.setString(3, GenderIdentity);
            mInsertUser.setString(4, SexualOrientation);
            mInsertUser.setString(5, Note);
            mInsertUser.executeUpdate();
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * Update the user information for a row in the database
     * 
     * @param username The username of the user who wants to update their gender identity
     * @param genderIdentity the gender identity of the user
     * 
     * @return The number of users that were inserted. -1 if there was an error
    */
    int updateUserGenderIdentity(String username, String genderIdentity) {
        int res = -1;
        try {
            mUpdateUserGenderIdentity.setString(1, genderIdentity);
            mUpdateUserGenderIdentity.setString(2, username);
            res = mUpdateUserGenderIdentity.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Update the user information for a row in the database
     * 
     * @param username The username of the user 
     * @param sexualOrientation the sexual orientation of the user
     * 
     * @return The number of users that were inserted. -1 if there was an error
    */
    int updateUserSexualOrientation(String username, String sexualOrientation) {
        int res = -1;
        try {
            mUpdateUserSexualOrientation.setString(1, sexualOrientation);
            mUpdateUserSexualOrientation.setString(2, username);
            res = mUpdateUserSexualOrientation.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the user information for a row in the database
     * 
     * @param username The username of the user 
     * @param userNote the note of the user
     * 
     * @return The number of users that were inserted. -1 if there was an error
    */
    int updateUserNote(String username, String userNote) {
        int res = -1;
        try {
            mUpdateUserNote.setString(1, userNote);
            mUpdateUserNote.setString(2, username);
            res = mUpdateUserNote.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Insert an Action into Action table
     * 
     * @param username username of the user
     * @param P_ID post id of the action
     * @param type type of action made
     * 
     * @return the number of actions added
     */
    int insertAction(int P_ID, String username, String type){
        int count =0;
        try {
            mInsertAction.setInt(1, P_ID);
            mInsertAction.setString(2, username);
            mInsertAction.setString(3, type);
            mInsertUser.executeUpdate();
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Update the upVotes for a post
     * 
     * @param P_ID post ID
     * @param upVote the number of up Votes on a post
     * @param username the username of the user
     * 
     * @return boolean to see if res or res2 = -1
    */
    boolean updateUpVotes(int P_ID, int upVote, String username) {
        int res = -1;
        int res2 = -1;
        try {
            mUpdateUpVotes.setInt(1, upVote);
            mUpdateUpVotes.setInt(2, P_ID);
            res = mUpdateUpVotes.executeUpdate();
            res++;

            mInsertAction.setInt(1, P_ID);
            mInsertAction.setString(2, username);
            mInsertAction.setString(3, "Upvote");
            res2 = mInsertAction.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (res!=-1 && res2!=-1);
    }

    /**
     * Update the down votes for a post
     * 
     * @param P_ID post ID
     * @param upVote the number of up Votes on a post
     * * @param username the username of the user
     * 
     * @return boolean to see if res or res2 = -1
    */
    boolean updateDownVotes(int P_ID, int upVote, String username) {
        int res = -1;
        int res2 = -1;
        try {
            mUpdateDownVotes.setInt(1, upVote);
            mUpdateDownVotes.setInt(2, P_ID);
            res = mUpdateDownVotes.executeUpdate();
            res++;

            mInsertAction.setInt(1, P_ID);
            mInsertAction.setString(2, username);
            mInsertAction.setString(3, "Downvote");
            res2 = mInsertAction.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (res!=-1 && res2!=-1);
    }

    /**
     * Insert a comment into the database
     * 
     * @param P_ID The post ID
     * @param Username The post content
     * @param Content content of the comment
     * 
     * @return The number of comments that were inserted
     */
    int insertComment(int P_ID, String username, String content) {
        int count = 0;
        try {
            mInsertComment.setInt(1, P_ID);
            mInsertComment.setString(2, username);
            mInsertComment.setString(3, content);
            mInsertComment.executeUpdate();
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Update a comment by id
     * 
     * @param content content of the comment 
     * @param C_ID id of comment
     * 
     * @return The number of comments that were inserted. -1 if there was an error
    */
    int updateComment(String content, int C_ID) {
        int res = -1;
        try {
            mUpdateComment.setString(1, content);
            mUpdateComment.setInt(2, C_ID);
            res = mUpdateComment.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get the data of One Post based on its ID
     * 
     * @param C_ID the ID of the comment
     * @return one comment row of data
     */

    String selectCommentByID(int C_ID){
        String res = null;
        try {
            mSelectCommentByID.setInt(1, C_ID);
            ResultSet rs = mSelectCommentByID.executeQuery();
            if (rs.next()) {
                res = rs.getString("Content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Select ALL comments from a certain user
     * 
     * @param username the username to find comments from
     * 
     * @return All comments, as an ArrayList
     */
    ArrayList<String> selectCommentByUsername(String username){
        ArrayList<String> res = new ArrayList<String>();
        try {
            mSelectCommentByUsername.setString(1, username);
            ResultSet rs = mSelectCommentByUsername.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("Content"));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Select ALL comments from a certain user
     * 
     * @param P_ID the id of post to find comments from
     * 
     * @return All comments, as an ArrayList
     */
    ArrayList<String> selectCommentByPost(int P_ID){
        ArrayList<String> res = new ArrayList<String>();
        try {
            mSelectCommentByPost.setInt(1, P_ID);
            ResultSet rs = mSelectCommentByPost.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("Content"));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the number of up votes from a post
     * 
     * @param P_ID the ID of the comment
     * @return the number up votes for the post
     */

    int selectUpVotesByID(int P_ID){
        int votes = -1;
        try {
            mSelectUpVotesByID.setInt(1, P_ID);
            ResultSet rs = mSelectUpVotesByID.executeQuery();
            if (rs.next()) {
                votes = rs.getInt("Upvote");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    /**
     * Get the number of up votes from a post
     * 
     * @param P_ID the ID of the comment
     * @return the number up votes for the post
     */

    int selectDownVotesByID(int P_ID){
        int votes = -1;
        try {
            mSelectDownVotesByID.setInt(1, P_ID);
            ResultSet rs = mSelectDownVotesByID.executeQuery();
            if (rs.next()) {
                votes = rs.getInt("Downvote");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    /**
     * Find if user exists in data already
     * @param username
     * @return boolean if user exists
     */
    boolean checkUser(String username){
        boolean exists = false;
        int count= 0;
        try{
            mCheckUser.setString(1, username);
            ResultSet rs = mCheckUser.executeQuery();
            if(rs.next()){
                count = rs.getInt("c");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count==1){
            exists = true;
        }
        return exists;
    }

    /**
     * Get the type of the last recent vote made by a user on a specific post
     * 
     * @param P_ID
     * @param username
     * 
     * @return type of vote made
     */
    String selectMostRecentVote(int P_ID, String username){
        String lastVote = "";
        try {
            mSelectMostRecentVote.setInt(2, P_ID);
            mSelectMostRecentVote.setString(1, username);
            ResultSet rs = mSelectMostRecentVote.executeQuery();
            if (rs.next()) {
                lastVote = rs.getString("Type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastVote;
    }

        //PHASE 3 STARTS HEREEEEEE

    /**
 * Get the link for a file based on its ID
 *
 * @param fileId The ID of the file to retrieve the link for
 * @return A string representing the link for the specified file
 */
public String getFileLink(int fileId) {
    String link = null;
    try {
        mSelectLinkById.setInt(1, fileId);
        ResultSet rs = mSelectLinkById.executeQuery();
        if (rs.next()) {
            link = rs.getString("Link");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return link;
}

/**
 * Get the data of one file based on the file ID
 *
 * @param fileId The ID of the file to retrieve
 * @return A FileData object representing the retrieved file
 */
public FileData getFileById(int fileId) {
    FileData res = null;
    try {
        mSelectOneById.setInt(1, fileId);
        ResultSet rs = mSelectOneById.executeQuery();
        if (rs.next()) {
            res = new FileData(rs.getInt("ID"), rs.getString("Name"), getFileLink(rs.getInt("ID")));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return res;
}

/**
 * Delete a file with the specified ID
 *
 * @param fileId The ID of the file to delete
 * @return A string indicating the status of the operation
 */
public String deleteFileById(int fileId) {
    try {
        mDeleteById.setInt(1, fileId);
        int rowsAffected = mDeleteById.executeUpdate();
        if (rowsAffected == 1) {
            return "File deleted successfully";
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "File deletion failed";
}

/**
 * Upload a new file to the server and store its link in the database
 *
 * @param name The name of the file
 * @param fileData The data of the file
 * @return A FileData object representing the uploaded file
 */
public FileData uploadFile(String name, byte[] fileData) {
    FileData res = null;
    try {
        mInsertFile.setString(1, name);
        mInsertFile.setBytes(2, fileData);
        int rowsAffected = mInsertFile.executeUpdate();
        if (rowsAffected == 1) {
            ResultSet rs = mInsertFile.getGeneratedKeys();
            if (rs.next()) {
                res = new FileData(rs.getInt(1), name, getFileLink(rs.getInt(1)));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return res;
}





}