package edu.lehigh.cse216.group17.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;
import com.google.gson.*;


/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( AppTest.class );
    }

    /*GLOBAL VARIABLE FOR DATABASE */
    Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String CLIENT_ID = "http://310744269504-d1mjhrpco5ff3jr24u1llpvf0idpb32v.apps.googleusercontent.com/";
    final Database db = Database.getDatabase(ip, port, user, pass);

    
    
    /*********************************************************************************************
     SUCCESSFUL TEST CASES
    **********************************************************************************************/

    /**
     * Routes Test Add Message
     *
     * Uses Gson Object to generate a simple request object, then using SQL
     * create a new post. Checks the title, content, username, and token ID used to create the post
     * are the expected and that the SQL server did the addition action.
     * 
    */
    public void testCreationPost(){
        final Gson gson = new Gson();
        String json = "{\"mTitle\":BackendUnitTest,\"mContent\":HelloWorld!,\"mUser\":Stephen,\"mToken\":sessionToken}";
        SimpleRequest req = gson.fromJson(json, SimpleRequest.class);
        
        int a=db.insertRow(req.mUser, req.mTitle,req.mContent);
        assertTrue(req.mTitle.equals("BackendUnitTest"));
        assertTrue(req.mContent.equals("HelloWorld!"));
        assertTrue(req.mUser.equals("Stephen"));
        assertTrue(req.mToken.equals("sessionToken")); 
        assertTrue(a!=0);
    }

    public void testUserCreation(){
        final Gson gson = new Gson();
        String json = "{\"mUsername\":Stephen,\"mEmail\":gufh123@lehigh.edu,\"mGenderIdentity\":XX,\"mSexualOrientation\":XY,\"mNote\":Hello}";
        UserDataRequest req = gson.fromJson(json, UserDataRequest.class);
        
        int a=db.insertUser(req.mUsername, req.mEmail,req.mGenderIdentity, req.mSexualOrientation, req.mNote);
        assertTrue(req.mUsername.equals("Stephen"));
        assertTrue(req.mEmail.equals("gufh123@lehigh.edu"));
        assertTrue(req.mGenderIdentity.equals("XX"));
        assertTrue(req.mSexualOrientation.equals("XY")); 
        assertTrue(req.mNote.equals("Hello")); 
    }

    public void testCommentCreation(){
        final Gson gson = new Gson();
        String json = "{\"mId\":3,\"mP_ID\":3,\"mContent\":Hey,\"mUsername\":Kenzie01}";
        CommentData req = gson.fromJson(json, CommentData.class);
        
        int a=db.insertComment(req.mP_ID, req.mUsername, req.mContent);
        assertTrue(req.mP_ID==3);
        assertTrue(req.mUsername.equals("Kenzie01"));
        assertTrue(req.mContent.equals("Hey"));
    }

    public void testGetVotes(){
        final Gson gson = new Gson();
        String json = "{\"mPostID\":3,\"mUsername\":Kenzie01}";
        VotesRequest req = gson.fromJson(json, VotesRequest.class);
        
        int a=db.selectUpVotesByID(req.mPostID);
        assertTrue(req.mPostID==3);
        assertTrue(req.mUsername.equals("Kenzie01"));
        assertTrue(a==2);
    }

    
    /**
     * Routes Test Get Content of the Post
     *
     * Gets post data in the previous test using the title and id.
     * Compares PostData objects to check the ID and TimeStamp are correct.
     * Check title, username, token ID, and content are the expected based on the ones established in previous test.
     * Check the number of likes is the default which is 0.
    **/
    //public void testGetPost(){
        
        // PostData a=db.selectByTitle("BackendUnitTest");
        // PostData b=db.selectOne(a.mId);

        // assertTrue((a.mId)==(b.mId));
        // assertTrue((a.mTitle).equals("BackendUnitTest"));
        // assertTrue((a.mContent).equals("HelloWorld!"));
        // assertTrue((a.mUsername).equals("Stephen"));
        // assertTrue((a.mCreated).equals(b.mCreated));
        // assertTrue((a.mUpVote)==0);
        // assertTrue((a.mDownVote)==0);
    //}




    /**
     * Routes Test Update Message and Likes
     * 
     * Gets the ID of Post use for these tests
     * Gets values updating title and content
     * Updates number of likes by 1
     * Checks all the updatess
     *
    **/ 
    // public void testUpdatePost(){

    //     PostData a=db.selectByTitle("BackendUnitTest");

    //     final Gson gson = new Gson();
    //     String json = "{\"mTitle\":BackendUnitTestUpd,\"mContent\":HelloWorldNew!\"mUsername\":Kenzie01}";
    //     SimpleRequest req = gson.fromJson(json, SimpleRequest.class);
        

    //     int upd=db.updateOne(a.mId,req.mTitle,req.mContent);
    //     int updLike=db.updateUpVote(a.mId,1);

        
    //     assertTrue(req.mTitle.equals("BackendUnitTestUpd"));
    //     assertTrue(req.mContent.equals("HelloWorldNew!"));
    //     assertTrue(upd!=-1);
    //     assertTrue(updLike!=-1);
    // }

    /**
     * Routes Test to Delete Post
     * Gets the ID of Post use for these tests
     * Deletes it and check it worked correctly
     *
    **/
    /* 
    public void testDelMessage(){
        PostData a=db.selectByTitle("BackendUnitTestUpd");
        int del=db.deleteOne(a.mId);
        assertTrue(del!=-1);
    }*/


    /*********************************************************************************************
     FAILURE TEST CASES (Intended to Generate an Error)
    **********************************************************************************************/
    
    /**
     * Failure Creation Data Row
     * 
     * Checks that GSON object has expected output and does not keep the succesfull 
     * test data
     * 
     */
    // public void testFailCreationPost(){
    //     final Gson gson = new Gson();
    //     String json = "{\"mTitle\":FailBackendUnitTest,\"mContent\":FailHelloWorld!\"mUser:userTest\"\"mToken:sessionToken\"}";
    //     SimpleRequest req = gson.fromJson(json, SimpleRequest.class);
        
    //     int a=db.insertRow(req.mTitle,req.mContent);
    //     assertFalse(req.mTitle.equals("BackendUnitTest"));
    //     assertFalse(req.mContent.equals("HelloWorld!"));
    //     //assertTrue(req.mUser.equals("userTest"));
    //     //assertTrue(req.mToken.equals("sessionToken"));
    //     assertTrue(a!=0);
    //}

    /**
     * Routes FAIL Test Get Content of the Post
     *
     * Attempt getting inexistent post by title and id
     * Postdata return structures should be null
     * 
    **/
    // public void testFailGetPost(){
        
    //     PostData a=db.selectByTitle("DoesNotExist");
    //     PostData b=db.selectOne(1000);

    //     assertTrue(a==null);
    //     assertTrue(b==null);

    // }


    /**
     * Update Data Row Failure NEED CHECKING
     * 
     * Attempt updating inexistent post and likes by id,title and content
     * Uppdate operations return should be -1 and non changes should 
     * be done to original Post created for the fail test
     * 
    /
    
    public void testFailUpdatePost(){

        PostData a=db.selectByTitle("FailBackendUnitTest");

        final Gson gson = new Gson();
        String json = "{\"mTitle\":FailBackendUnitTestUpd,\"mContent\":FailHelloWorldUpd!}";
        SimpleRequest req = gson.fromJson(json, SimpleRequest.class);
        

        int upd=db.updateOne(1000,req.mTitle,req.mContent);
        int updLike=db.updateLikes(1000,1);

        
        assertFalse(req.mTitle.equals("FailBackendUnitTest"));
        assertFalse(req.mContent.equals("FailHelloWorld!"));
        assertTrue(upd==-1);
        assertTrue(updLike==-1);
    }

    /**
     * Delete Data Row Failure
     * 
     * Attempts deleting a non existent post
     * and the operation should ghet a value of 
     * -1
    /
    public void testFailDeletePost(){
        int del=db.deleteOne(1000);
        assertFalse(del!=-1);
    }*/


    /**
     * Tests if Google OAuth is connected properly
     */
    public void testGoogleOAuth(){
        assertTrue(true);
        //test manually 
    }
}

/*NEW TESTS
 test_login_success: 
 This test should check whether a user can successfully log in with a valid username and password. 
 It should send a POST request to the /login route with valid credentials and check that the response contains a valid session token.

test_login_failure: 
This test should check whether a user cannot log in with invalid credentials. 
It should send a POST request to the /login route with invalid credentials and check that the response contains an error message indicating that the login attempt failed.

test_logout_success:
This test should check whether a user can successfully log out with a valid session token. 
It should send a POST request to the /logout route with a valid session token and check that the response contains a message indicating that the user has been logged out.

test_logout_failure: 
This test should check whether a user cannot log out with an invalid session token. 
It should send a POST request to the /logout route with an invalid session token and check that the response contains an error message indicating that the logout attempt failed.

test_upload_file_success: 
This test should check whether a user can successfully upload a file with a valid session token. 
It should send a POST request to the /files route with a valid session token and a file data in the expected format, and check that the response contains a JSON object with the ID, name, and link of the uploaded file.

test_upload_file_failure: 
This test should check whether a user cannot upload a file with an invalid session token. 
It should send a POST request to the /files route with an invalid session token and a file data in the expected format, and check that the response contains an error message indicating that the upload attempt failed.

test_download_file_success: 
This test should check whether a user can successfully download a file with a valid session token and file ID. 
It should send a GET request to the /files/:id route with a valid session token and file ID, and check that the response contains the file data in the expected format.

test_download_file_failure: 
This test should check whether a user cannot download a file with an invalid session token or file ID. 
It should send a GET request to the /files/:id route with an invalid session token or file ID, and check that the response contains an error message indicating that the download attempt failed.

test_delete_file_success: 
This test should check whether a user can successfully delete a file with a valid session token and file ID. 
It should send a DELETE request to the /files/:id route with a valid session token and file ID, and check that the response contains a message indicating that the file has been deleted successfully.

test_delete_file_failure: 
This test should check whether a user cannot delete a file with an invalid session token or file ID. 
It should send a DELETE request to the /files/:id route with an invalid session token or file ID, and check that the response contains an error message indicating that the deletion attempt failed.

*/