package edu.lehigh.cse216.group17.admin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

import edu.lehigh.cse216.group17.admin.PostData;
import edu.lehigh.cse216.group17.admin.CommentData;
import edu.lehigh.cse216.group17.admin.VotesRequest;
import edu.lehigh.cse216.group17.admin.UserDataRequest;
import edu.lehigh.cse216.group17.admin.SimpleRequest;
import edu.lehigh.cse216.group17.admin.StructuredResponse;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;

// import edu.lehigh.cse216.group17.backend.GoogleDrive;
// import edu.lehigh.cse216.group17.backend.credentials;

/**
 * Database class
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

     /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;

     /**
     * A prepared statement for creating the user table in our database
     */
    private PreparedStatement mCreateUserTable;
    
     /**
     * A prepared statement for creating the post table in our database
     */
    private PreparedStatement mCreatePostTable;


     /**
     * A prepared statement for creating the action table in our database
     */
    private PreparedStatement mCreateActionTable;


    /**
     * A prepared statement for creating the comment table in our database
     */
    private PreparedStatement mCreateCommentTable;

    /**
     * A prepared statement for deleting user 
     */
    private PreparedStatement mDeleteUser;

    /**
     * A prepared statement for seeing if a table exists
     */
    private PreparedStatement mTableExist;

    /**
     * A prepared statement for adding a column to the user table
     */
    private PreparedStatement mAddColumnToUser;

    /**
     * A prepared statement for dropping a column in the user table
     */
    private PreparedStatement mDropColumnInUser;

    /**
     * A prepared statement for adding a column to the post table
     */
    private PreparedStatement mAddColumnToPost;

    /**
     * A prepared statement for dropping a column in the post table
     */
    private PreparedStatement mDropColumnInPost;

    /**
     * A prepared statement for adding a column to the comment table
     */
    private PreparedStatement mAddColumnToComment;

    /**
     * A prepared statement for dropping a column in the column table
     */
    private PreparedStatement mDropColumnInComment;

    /**
     * A prepared statement for adding a column to the action table
     */
    private PreparedStatement mAddColumnToAction;

    /**
     * A prepared statement for dropping a column in the action table
     */
    private PreparedStatement mDropColumnInAction;

    /**
     * A prepared statement for updating a user's validation status
     */
    private PreparedStatement mUpdateUserValidation;

    /**
     * A prepared statement for updating a post's validation status
     */
    private PreparedStatement mUpdatePostValidation;

    /**
     * A prepared statement for selecting a user's validation status
     */
    private PreparedStatement mGetUserValidationStatus;

     /**
     * A prepared statement for selecting a post's validation status
     */
    private PreparedStatement mGetPostValidationStatus;

    /**
     * A prepared statement to create a file table
     */
    private PreparedStatement mCreateFileTable;

    /**
     * A prepared statement for adding a column to the Post table
     */
    private PreparedStatement mAddFileColumnToPost;

    /**
     * A prepared statement for adding a column to the Comment table
     */
    private PreparedStatement mAddFileColumnToComment;

    /**
     * Prepared Statement for recieving string input
     */
    private PreparedStatement mStringInput;

    /**
     * Prepared Statement for adding a file
     */
    private PreparedStatement mInsertFile;

    /**
     * Prepared Statement for listing documents
     */
    private PreparedStatement mListDocuments;

    /**
     * Prepared statement to update the file's validation status
     */
    private PreparedStatement mUpdateFileValidation;
    
    /**
     * Prepared statement to get the file's validation status
     */
    private PreparedStatement mGetFileValidationStatus;

    /**
     * Prepared statement for deleting a file
     */
    private PreparedStatement mDeleteFile;

    /**
     * Prepared Statement for ordering the files 
     */
    private PreparedStatement mOrderFiles;


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
             //db.mCreateTable = db.mConnection.prepareStatement(
                    //"CREATE TABLE tblData (id SERIAL PRIMARY KEY, subject VARCHAR(50) "
                            //+ "NOT NULL, message VARCHAR(1024) NOT NULL)");
            db.mCreateUserTable = db.mConnection.prepareStatement(
                    "CREATE TABLE USERDATA (Username varchar(50), email VARCHAR(50), gender_identity VARCHAR(50), sexual_orientation VARCHAR(50), Note VARCHAR(50))"
                            );
            db.mCreatePostTable = db.mConnection.prepareStatement(
                    "CREATE TABLE POST (ID int, Title VARCHAR(200), Content VARCHAR(200), Created TIMESTAMP DEFAULT NOW(), Up_Vote int, Down_Vote int)"
                            );
            db.mCreateCommentTable = db.mConnection.prepareStatement(
                "CREATE TABLE COMMENT (ID int, Content VARCHAR(200), Created TIMESTAMP DEFAULT NOW())"
                        );
            db.mCreateActionTable = db.mConnection.prepareStatement(
                "CREATE TABLE ACTION (AID int, Type VARCHAR(200), Tms TIMESTAMP DEFAULT NOW())"
                        );        
            db.mTableExist = db.mConnection.prepareStatement("SELECT 1 FROM information_schema.tables WHERE table_name = ?");

            db.mCreateFileTable = db.mConnection.prepareStatement(
                "CREATE TABLE FILES (File_ID VARCHAR(50), URL VARCHAR(200), Post_ID int, Comment_ID int, Username VARCHAR(50), Last_Accessed TIMESTAMP DEFAULT NOW(), Validation VARCHAR(1))"
                        );
            
            db.mInsertFile = db.mConnection.prepareStatement("INSERT INTO FILES (File_ID, URL, Post_ID, Comment_ID, Username, Validation) VALUES (?,?,?,?,?,?)");
            
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE FILES");
            db.mSelectAll=db.mConnection.prepareStatement("SELECT * FROM POST");
            db.mSelectOne=db.mConnection.prepareStatement("SELECT * FROM POST WHERE ID=?");
            db.mSelectOnebyTitle=db.mConnection.prepareStatement("SELECT * FROM POST WHERE TITLE=?");
            db.mInsertOne=db.mConnection.prepareStatement("SET timezone = 'America/New_York';"+
                                                         "INSERT INTO POST VALUES (default,?,?,?,default,default,default)");
            db.mUpdateOne=db.mConnection.prepareStatement("UPDATE POST SET TITLE = ?, CONTENT=? WHERE id = ?");
            db.mDeleteOne=db.mConnection.prepareStatement("DELETE FROM POST WHERE ID = ?");
            db.mDeleteUser=db.mConnection.prepareStatement("DELETE FROM USERDATA WHERE email = ?");
            db.mGetLikes=db.mConnection.prepareStatement("SELECT LIKES FROM POST WHERE ID=?");
            /*db.mUpdateLikes=db.mConnection.prepareStatement("UPDATE POST SET LIKES = ? WHERE id = ?;"+
                                                            "SET timezone = 'America/New_York';"+
                                                            "INSERT INTO ACTION VALUES (default,?,'Update Likes',default);");*/

            db.mInsertUser=db.mConnection.prepareStatement("INSERT INTO USERDATA VALUES (?,?,?,?,?)");
            db.mUpdateUserGenderIdentity=db.mConnection.prepareStatement("UPDATE USERDATA SET GENDERIDENTITY = ? WHERE USERNAME = ?");
            db.mUpdateUserSexualOrientation=db.mConnection.prepareStatement("UPDATE USERDATA SET SEXUALORIENTATION = ? WHERE USERNAME = ?");
            db.mUpdateUserNote=db.mConnection.prepareStatement("UPDATE USERDATA SET NOTE = ? WHERE USERNAME = ?");
            //UpdateUserValidation prepared statement
            db.mUpdateOne=db.mConnection.prepareStatement("UPDATE POST SET TITLE = ?, CONTENT=? WHERE id = ?");
            db.mDeleteOne=db.mConnection.prepareStatement("DELETE FROM POST WHERE ID = ?");
            //When calling on delete one, call upon the invalidate user
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
            
            
            db.mAddColumnToUser=db.mConnection.prepareStatement("ALTER TABLE USERDATA ADD VALIDATION VARCHAR(1)");
            db.mDropColumnInUser=db.mConnection.prepareStatement("ALTER TABLE USERDATA DROP COLUMN VALIDATION");
            db.mAddColumnToPost=db.mConnection.prepareStatement("ALTER TABLE POST ADD VALIDATION VARCHAR(1)");
            db.mDropColumnInPost=db.mConnection.prepareStatement("ALTER TABLE POST DROP COLUMN VALIDATION");
            db.mAddFileColumnToPost=db.mConnection.prepareStatement("ALTER TABLE POST ADD FILE_ID int");
            db.mAddFileColumnToComment=db.mConnection.prepareStatement("ALTER TABLE COMMENT ADD FILE_ID int");

            db.mUpdatePostValidation=db.mConnection.prepareStatement("UPDATE POST SET VALIDATION = ? WHERE id = ?");
            db.mUpdateUserValidation=db.mConnection.prepareStatement("UPDATE USERDATA SET VALIDATION = ? WHERE USERNAME = ?");

            db.mGetPostValidationStatus=db.mConnection.prepareStatement("SELECT VALIDATION FROM POST WHERE ID=?");
            db.mGetUserValidationStatus=db.mConnection.prepareStatement("SELECT VALIDATION FROM USERDATA WHERE USERNAME=?");

            db.mUpdateFileValidation=db.mConnection.prepareStatement("UPDATE FILES SET VALIDATION = ? WHERE File_ID = ?");
            db.mGetFileValidationStatus=db.mConnection.prepareStatement("SELECT VALIDATION FROM FILES WHERE File_ID=?");

            db.mStringInput=db.mConnection.prepareStatement("INSERT INTO FILES");

            db.mListDocuments=db.mConnection.prepareStatement("SELECT File_ID FROM FILES WHERE Validation=T");

            db.mDeleteFile=db.mConnection.prepareStatement("DELETE FROM FILES WHERE File_ID = ?");

            db.mOrderFiles=db.mConnection.prepareStatement("SELECT File_ID FROM FILES ORDER BY Last_Accessed ASC");

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
                rs.getString("Content"),rs.getTimestamp("Created"),rs.getInt("Upvote"), 
                rs.getInt("Downvote"), rs.getString("Username")));
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
     * @param ID the ID of the post
     * @return one post row of data
     */

     PostData selectOne(int ID){
        PostData res = null;
        try {
            mSelectOne.setInt(1, ID);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new PostData(rs.getInt("ID"), rs.getString("Title"), 
                rs.getString("Content"),rs.getTimestamp("Created"),
                rs.getInt("Upvote"), rs.getInt("Downvote"), rs.getString("Username"));
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
                rs.getString("Content"),rs.getTimestamp("Created"),
                rs.getInt("Upvote"), rs.getInt("Downvote"), rs.getString("Username"));
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
     * @param id The id of the post to delete
     * 
     * @return The number of posts that were deleted.  -1 indicates an error.
     */
    int deleteOne(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    /** 
     * @param email
     * @return int
     */
    int deleteUser(String email) {
        int res = -1;
        try {
            mDeleteUser.setString(1, email);
            res = mDeleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get the number of Likes of One Post based on its ID
     * 
     * @param ID the ID of the post
     * @return number of likes. -1 if there is an error
     */

    int getLikes(int ID){
        int res = -1;
        try {
            mGetLikes.setInt(1, ID);
            ResultSet rs = mGetLikes.executeQuery();
            if (rs.next()) {
                res = rs.getInt("Likes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the number of likes for a post row in the database
     * 
     * @param title The post title
     * @param content The post content
     * 
     * @return The number of rows that were inserted. -1 if there was an error
    */

    /*int updateLikes(int id, int n_likes) {
        int res = -1;
        try {
            mUpdateLikes.setInt(1, n_likes);
            mUpdateLikes.setInt(2, id);
            mUpdateLikes.setInt(3, id);
            res = mUpdateLikes.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }*/

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



     /**
     * Create tblData. If it already exists, this will print an error
     */
    void createTable() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database. If it does not exist, this will print
     * an error.
     */
    void dropTable() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createUserTable() {
        try {
            mCreateUserTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createPostTable() {
        try {
            mCreatePostTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createCommentTable() {
        try {
            mCreateCommentTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createActionTable() {
        try {
            mCreateActionTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createFileTable() {
        try {
            mCreateFileTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /** 
     * @param tableName the name of the table
     * @return boolean
     * @throws SQLException throws an SQL exception
     */
    public boolean tableExists(String tableName) throws SQLException {
        boolean tableExists = false;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = mTableExist;
            statement.setString(1, tableName);
            resultSet = statement.executeQuery();
            tableExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableExists;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int insertUserColumn() {
        int res = -1;
        try {
            mAddColumnToUser.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int deleteUserColumn() {
        int res = -1;
        try {
            mDropColumnInUser.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int insertPostColumn() {
        int res = -1;
        try {
            mAddColumnToPost.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int deletePostColumn() {
        int res = -1;
        try {
            mDropColumnInPost.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the user information for a row in the database
     * 
     * @param username The username of the user who needs their validation status updated
     * @param validationStatus validation status of the user
     * 
     * @return The 1 if the row was updated
    */
    int updateUserValidation(String username, String validationStatus) {
        int res = -1;
        try {
            mUpdateUserValidation.setString(1, validationStatus);
            mUpdateUserValidation.setString(2, username);
            res = mUpdateUserValidation.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the user information for a row in the database
     * 
     * @param P_ID The post ID of the post that needs its validation status updated
     * @param validationStatus validation status of the user
     * 
     * @return The 1 if the row was updated
    */
    int updatePostValidation(int P_ID, String validationStatus) {
        int res = -1;
        try {
            mUpdatePostValidation.setString(1, validationStatus);
            mUpdatePostValidation.setInt(2, P_ID);
            res = mUpdatePostValidation.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }  

    /**
     * Get the validation status of a post
     * 
     * @param ID the ID of the post
     * @return if the post is valid or not
     */

    String getPostStatus(int ID){
        String res = " ";
        try {
            mGetPostValidationStatus.setInt(1, ID);
            ResultSet rs = mGetPostValidationStatus.executeQuery();
            if (rs.next()) {
                res = rs.getString("VALIDATION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the file information for a row in the database
     * 
     * @param F_ID The file ID of the post that needs its validation status updated
     * @param validationStatus validation status of the user
     * 
     * @return The 1 if the row was updated
    */
    int updateFileValidationStatus(String F_ID, String validationStatus) {
        int res = -1;
        try {
            mUpdateFileValidation.setString(1, validationStatus);
            mUpdateFileValidation.setString(2, F_ID);
            res = mUpdateFileValidation.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    } 

    /**
     * Get the validation status of a post
     * 
     * @param ID the ID of the post
     * @return if the post is valid or not
     */

    String getFileStatus(String ID){
        String res = " ";
        try {
            mGetFileValidationStatus.setString(1, ID);
            ResultSet rs = mGetFileValidationStatus.executeQuery();
            if (rs.next()) {
                res = rs.getString("VALIDATION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Get the validation status of a user
     * 
     * @param ID the ID of the post
     * @return if the user is valid or not
     */

    String getUserStatus(String username){
        String res = " "; 
        try {
            mGetUserValidationStatus.setString(1, username);
            ResultSet rs = mGetUserValidationStatus.executeQuery();
            if (rs.next()) {
                res = rs.getString("VALIDATION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int insertFileColumnToPost() {
        int res = -1;
        try {
            mAddFileColumnToPost.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @return int that shows if SQL command ran
     */
    int insertFileColumnToComment() {
        int res = -1;
        try {
            mAddFileColumnToComment.execute();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Inserts a User into USERDATA
     * 
     * @param File_ID The file id
     * @param URL The file URL
     * @param Post_ID the post id
     * @param Comment_ID the comment id
     * @param Username the user's username
     */
    int insertFile(String URL, int Post_ID, int Comment_ID, String Username){
        int count =0;
        try {
            mInsertFile.setString(1, URL.substring(URL.lastIndexOf('/') + 1));
            mInsertFile.setString(2, URL);
            mInsertFile.setInt(3, Post_ID);
            mInsertFile.setInt(4, Comment_ID);
            mInsertFile.setString(5, Username);
            mInsertFile.setString(6,"T");
            mInsertFile.executeUpdate();
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Lists the valid files and the information included
     * 
     */
    // void listAllValidFileID(){
    //     ArrayList<String> res = new ArrayList<String>();
    //     try {
    //         ResultSet rs = mListDocuments.executeQuery();
    //         while (rs.next()) {
    //             res.add(rs.getString("File_ID"));
    //         }
    //         rs.close();
    //         for (String fileId : res) {
    //             // create Drive API client instance
    //             Drive drive = new Drive.Builder(transport, jsonFactory, credential)
    //             .setApplicationName("My App Name")
    //             .build();

    //             // retrieve file metadata
    //             File file = drive.files().get(fileId).setFields("id, name, size, modifiedTime").execute();

    //             // print file information
    //             System.out.print("File name: " + file.getName());
    //             System.out.print("  File ID: " + file.getId());
    //             System.out.print("  File size: " + file.getSize() + " bytes");
    //             System.out.print("  Last retrieved: " + file.getModifiedTime());
    //             System.out.print("  Owner: " + file.getOwners());
    //             System.out.println();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * Delete a file row by file ID
     * 
     * @param file_id The id of the file to delete
     * 
     * @return The number of posts that were deleted.  -1 indicates an error.
     */
    int deleteFile(String file_id) {
        int res = -1;
        try {
            mDeleteFile.setString(1, file_id);
            res = mDeleteFile.executeUpdate();
            res++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Lists the valid files and the information included
     * 
     */
    void orderFilesByDateAccessed(){
        ArrayList<String> res = new ArrayList<String>();
        try {
            ResultSet rs = mOrderFiles.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("File_ID"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}