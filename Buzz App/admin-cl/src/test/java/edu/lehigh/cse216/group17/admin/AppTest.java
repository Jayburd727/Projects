package edu.lehigh.cse216.group17.admin;
import edu.lehigh.cse216.group17.admin.Database;
//import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


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
    //public static Test suite(){
       // return new TestSuite( AppTest.class );
    //}

    /*GLOBAL VARIABLE FOR DATABASE */
    
    
    private Database db;

    @Before
    public void setUp() {
        // Initialize the database connection
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        db = Database.getDatabase(ip, port, user, pass);
        System.out.println(db);
    }
    
    
    
    
    /*********************************************************************************************
     SUCCESSFUL TEST CASES
    **********************************************************************************************/

    /**
     * Test output
     *
     * Tests insertion of a row
     * 
    **/
   /*  public void testCreationPost(){
        
        int a=db.insertRow("AdminUnitTest","Adminreq.mContent");

        assertTrue(a!=0);
    }*/

    
    /**
     * Routes Test Get Content of the Post
     *
     * Gets post data in the previous test using the title and id.
     * Compares PostData objects to check the ID and TimeStamp are correct.
     * Check title and content are the expected based on the ones established in previous test.
     * Check the number of likes is the default which is 0.
    **/
   /*  public void testGetPost(){
        
        PostData a=db.selectByTitle("BackendUnitTest");
        //PostData b=db.selectOne(a.mId);

        assertTrue((a.mId)==(b.mId));
        assertTrue((a.mTitle).equals("BackendUnitTest"));
        assertTrue((a.mContent).equals("HelloWorld!"));
        assertTrue((a.mCreated).equals(b.mCreated));
       // assertTrue(a.mLikes==0);

    }*/

    /**
     * Routes Test Update Message and Likes
     * 
     * Gets the ID of Post use for these tests
     * Gets values updating title and content
     * Updates number of likes by 1
     * Checks all the updatess
     *
    **/
    /* 
    public void testUpdatePost(){

        PostData a=db.selectByTitle("BackendUnitTest");

        final Gson gson = new Gson();
        String json = "{\"mTitle\":BackendUnitTestUpd,\"mContent\":HelloWorldNew!}";
        SimpleRequest req = gson.fromJson(json, SimpleRequest.class);
        

        int upd=db.updateOne(a.mId,req.mTitle,req.mContent);
        int updLike=db.updateLikes(a.mId,1);

        
        assertTrue(req.mTitle.equals("BackendUnitTestUpd"));
        assertTrue(req.mContent.equals("HelloWorldNew!"));
        assertTrue(upd!=-1);
        assertTrue(updLike!=-1);
    }*/

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
     * Routes FAIL Test Get Content of the Post
     *
     * Attempt getting inexistent post by title and id
     * Postdata return structures should be null
     * 
    **/
    @Test
    public void testFailGetPost(){
        
        PostData a=db.selectByTitle("DoesNotExist");
        PostData b=db.selectOne(1000);

        assertTrue(a==null);
        assertTrue(b==null);

    }


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
     * test creation of the table successfully
     /
     public void testCreationTable()={
        check whether the table is created successfully and 
        the name of the corresponding column is right.
     }
     */

     /**
      * test invalid ideas are not shown anymore
      /
      public void invalidIdea{
        test idea will not been seen
      }
      */

      /**
       * test invalid users can't login
       /
       public void invalidUser{
        when invalid user try to login, it will fail.
       }

       */

        @Test
       public void testSelectAll(){
       
        
        ArrayList<PostData> a=db.selectAll();

        assertTrue(a!=null);
    }

    /*public void testSelectOne(){
          PostData a = db.selectOne(1);
          assertTrue(a!=null);


    }*/
    @Test
    public void testCreateTables() {
        
        
        try {
            
            
            // create new tables
            db.createUserTable();
            db.createPostTable();
            db.createCommentTable();
            db.createActionTable();
            
            // make sure tables were created successfully
            assertTrue("User table was not created", db.tableExists( "userdata"));
            assertTrue("Post table was not created", db.tableExists( "post"));
            assertTrue("Comment table was not created", db.tableExists("comment"));
            assertTrue("Action table was not created", db.tableExists("action"));
            
        } catch (SQLException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
        db.disconnect();
    }
    
    // @Test
    // public void testDeletePost(){
        
    //         int a = db.deleteOne(1);
    //         assertTrue(a!=-1);
    //         db.disconnect();

    // }
    
    //  public void testInsertUser(){
    //     int a = db.insertUser("new", "abc@123", "male", "female", " ");
    //     assertTrue(a!=0);
    // }
    // @Test
    // public void testDeleteUser(){
    //     int b = db.deleteUser("abc@123");
    //     assertTrue(b!=-1);
    //     db.disconnect();

    // }

    @Test
    public void testUpdateUserValidation(){
        int upd=db.updateUserValidation("Stephen","T");
        assertTrue(upd!=0);
    }

    // @Test
    // public void testUpdatePostValidation(){
    //     int upd=db.updatePostValidation(33,"T");
    //     assertTrue(upd!=0);
    // }

    @Test
    public void testUserValidationStatus(){
        String a=db.getUserStatus("Stephen");
        assertTrue(a.equals("T"));
    }

    // @Test
    // public void testPostValidationStatus(){
    //     String a=db.getPostStatus(33);
    //     assertTrue(a.equals("T"));
    // }

    // @Test
    // public void testInsertFile(){
    //     int a=db.insertFile("helloWorld.com/ahduagd", 33, 33, "Stephen");
    //     assertTrue(a!=0);
    // }

    //Test updating a file validation
    //@Test
    // public void testUpdateFileValidation(){
    //     int upd=db.updateFileValidationStatus("helloWorld.com/ahduagd","T");
    //     assertTrue(upd!=-1);
    // }

    //Test getting the file's validation
    // @Test
    // public void testFileValidationStatus(){
    //     String a=db.getFileStatus("helloWorld.com/ahduagd");
    //     assertTrue(a.equals("T"));
    // }

    //Test deleting a file
    // @Test
    // public void testDeleteFile(){
    //     int a=db.deleteFile("helloWorld.com/ahduagd");
    //     assertTrue(a!=-1);
    // }
}
