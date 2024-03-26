package edu.lehigh.cse216.group17.backend;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.BucketInfo;
import java.io.FileInputStream; //used to read data from a file as a stream of bytes
import java.security.GeneralSecurityException;
import com.google.auth.oauth2.GoogleCredentials; //allows access to class that provides a way to load and manage service account credentials in Java

import com.google.api.client.*;

// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;

// Import Google's JSON library
import com.google.gson.*;

// Import other Java Useful Libraries
import java.util.*;

/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {  //A java class named App that is public (it can be accessed from any other class)
    /**
     * Main method
     * 
     * @param args arguments
     */
    public static void main(String[] args) { //public 

        // Uses Gson library which is a serialization library that can be used to convert java objects into their JSON representation
        final Gson gson = new Gson();   //initlizes a Gson instance that can be used to convert java objects to JSON it can't be modified (final)

        // Start and Connect to database
        //Example System.getenv("PATH") Key is PATH value would be /usr/bin...
        Map<String, String> env = System.getenv();  //Creates a map instance called (env) that uses strings for both key and value types. Map is initialized with the key-value pairs of the enviroment variables of the current system
        String ip = env.get("POSTGRES_IP"); //retrieves the value of the enviroment variable named (POSTGRES_IP) and assigns it to the string variable (ip)
        String port = env.get("POSTGRES_PORT"); //retrieves the value of the enviroment variable named (POSTGRES_PORT) and assigns it to the string variable (port)
        String user = env.get("POSTGRES_USER"); //retrieves the value of the enviroment variable named (POSTGRES_USER) and assigns it to the string variable (user)
        String pass = env.get("POSTGRES_PASS"); //retrieves the value of the enviroment variable named (POSTGRES_PASS) and assigns it to the string variable (pass)
        String CLIENT_ID = env.get("CLIENT_ID"); //retrieves the value of the enviroment variable named (CLIENT_ID) and assigns it to the string variable (CLIENT_ID)
        final Database db = Database.getDatabase(ip, port, user, pass); //creates a new Database object by calling getDatabase() of the Database class and passes in four parameters ip, port, user, pass which is then assigned to final variable (db)

        Random random = new Random();   //random class generates random numbers
        HashMap userStore = new HashMap<String, Integer>(); //creates a hashmap object called userStore that will store String keys and Integer Values
        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 8998)); //calls the getIntFromEnv() method to retreive the value of the (PORT) enviromental variable and converts it to an integer which returns the value of 8998 and Spark.port() sets the port number for Spark

        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION"); //Creates a string called (static_location_override) and retrieves the value of the enviromental variable (STATIC LOCATION)
        if (static_location_override == null) { //if (static_location_override) is empty
            Spark.staticFileLocation("/web"); //sets the static file location for a Spark web application where /web specifies the directory where the static files are located. (static like HTML, CSS, JavaScript files)
        } else {    //if (static_location_override) isn't empty then
            Spark.staticFiles.externalLocation(static_location_override); //sets the external static file location for a Spark web application using the "Spark.staticFiles.externalLocation()" method. The parameter "static_location_override" is a String variable that contains the directory where the external static files are located
        }

        // Set up a route for serving the main page
        //(req) is request object that contains information about the incoming request
        //(res) is a response object that is used to generate the response to send back to the client
        Spark.get("/", (req, res) -> {  //Spark.get() allows to define a route handler for the GET method and the root URL ("/")
            res.redirect("/index.html");    //redirect use to /index.html which is assumed to be in the root directory of the application
            return "";  //lambda returns empty string because its expected to return a value
        });

        // Authenticate
        authenticate(gson, db, random, userStore, CLIENT_ID);

        // Java Spark API Operations
        try {
            getDrive(args);
            getMessages(gson, db);
            getMessage(gson, db);
            newMessage(gson, db);
            updateMessage(gson, db);
            deleteMessage(gson, db);
            newUser(gson, db);
            updateGenderIdentity(gson, db);
            updateSexualOrientation(gson, db);
            updateNote(gson, db);
            updateUpVote(gson, db);
            updateDownVote(gson, db);
            newComment(gson, db);
            updateComment(gson, db);
            getCommentByID(gson, db);
            getCommentByUsername(gson, db);
            getCommentByPost(gson, db);
            getUpVoteByPost(gson, db);
            getDownVoteByPost(gson, db);
            getLatestVote(gson, db);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void getDrive(String[] args) throws IOException, GeneralSecurityException {
        GoogleDrive googleDrive = new GoogleDrive();
        googleDrive.main(args);
    }

    /**
     * GET route that returns ALL Posts Data. All we do is get
     * the data, embed it in a StructuredResponse, turn it into JSON, and
     * return it. If there's no data, we return "[]", so there's no need
     * for error handling.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getMessages(Gson gson, Database db) {
        Spark.get("/messages", (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("OK", null, db.selectAll()));
        });
    }

    /**
     * GET route that returns every data field from a SINGLE Post in the DataBase.
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getMessage(Gson gson, Database db) {
        Spark.get("/messages/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            PostData data = db.selectOne(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * POST route for adding a new Post to the Database. This will read
     * JSON from the body of the request, turn it into a SimpleRequest
     * object, extract the title and message, insert them, and return the
     * ID of the newly created row.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void newMessage(Gson gson, Database db) {
        Spark.post("/messages", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = db.insertRow(req.mUser, req.mTitle, req.mContent);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
    }

    /**
     * PUT route for updating a POST Title and Content in the DataBase.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateMessage(Gson gson, Database db) {
        Spark.put("/messages/:id", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateOne(idx, req.mTitle, req.mContent);

            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
    }

    /**
     * DELETE route for removing a row from the DataStore
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void deleteMessage(Gson gson, Database db) {
        Spark.delete("/messages/:id", (request, response) -> {
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = db.deleteOne(idx);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });
    }

    // ----------------------------------------------------------------------------------//
    // BELOW THIS POINT ARE THE ROUTES I HAVE QUESTIONS ON AND WILL NEED TO UPDATE
    // ----------------------------------------------------------------------------------//

    /**
     * POST route for adding a new User to the Database. This will read
     * JSON from the body of the request, turn it into a SimpleRequest
     * object, extract the title and message, insert them, and return the
     * ID of the newly created row.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void newUser(Gson gson, Database db) {
        Spark.post("/addUser", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            UserDataRequest req = gson.fromJson(request.body(), UserDataRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = db.insertUser(req.mUsername, req.mEmail, req.mGenderIdentity, req.mSexualOrientation,
                    req.mNote);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
    }

    /**
     * PUT route for updating a user's gender identity
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateGenderIdentity(Gson gson, Database db) {
        Spark.put("/updateUser/genderIdentity/:username", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            String username = request.params("username");
            System.out.println(username);
            UserDataRequest req = gson.fromJson(request.body(), UserDataRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateUserGenderIdentity(username, req.mGenderIdentity);

            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row ", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
    }

    /**
     * PUT route for updating a user's sexual orientation
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateSexualOrientation(Gson gson, Database db) {
        Spark.put("/updateUser/sexualOrientation/:username", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            String username = request.params("username");
            UserDataRequest req = gson.fromJson(request.body(), UserDataRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateUserSexualOrientation(username, req.mSexualOrientation);

            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row ", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
    }

    /**
     * PUT route for updating a user's note
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateNote(Gson gson, Database db) {
        Spark.put("/updateUser/note/:username", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            String username = request.params("username");
            UserDataRequest req = gson.fromJson(request.body(), UserDataRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateUserNote(username, req.mNote);

            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row ", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
    }

    /**
     * PUT route for updating a upvote
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateUpVote(Gson gson, Database db) {
        Spark.put("/upVote/update/:username", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            String username = request.params("username");
            VotesRequest req = gson.fromJson(request.body(), VotesRequest.class);
            String vote = db.selectMostRecentVote(req.mPostID, req.mUsername);
            int count = db.selectUpVotesByID(req.mPostID);
            int downCount = db.selectDownVotesByID(req.mPostID);
            boolean update;
            boolean downUpdate;
            response.status(200);
            response.type("application/json");
            if (vote.equals("Downvote")) {
                downCount -= 1;
                downUpdate = db.updateDownVotes(req.mPostID, downCount, req.mUsername);
                count += 1;
                update = db.updateUpVotes(req.mPostID, count, req.mUsername);
            } else if (vote.equals("Upvote")) {
                count -= 1;
                update = db.updateUpVotes(req.mPostID, count, req.mUsername);
                return gson.toJson(new StructuredResponse("ok", null, update));
            } else {
                count += 1;
                update = db.updateUpVotes(req.mPostID, count, req.mUsername);
            }

            if (!update) {
                return gson.toJson(new StructuredResponse("error", "unable to update row ", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, update));
            }
        });
    }

    /**
     * PUT route for updating a down Vote
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateDownVote(Gson gson, Database db) {
        Spark.put("/downVote/update/:username", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            String username = request.params("username");
            VotesRequest req = gson.fromJson(request.body(), VotesRequest.class);
            String vote = db.selectMostRecentVote(req.mPostID, req.mUsername);
            int count = db.selectDownVotesByID(req.mPostID);
            int upCount = db.selectUpVotesByID(req.mPostID);
            boolean update;
            boolean upUpdate;
            response.status(200);
            response.type("application/json");
            if (vote.equals("Upvote")) {
                upCount -= 1;
                upUpdate = db.updateUpVotes(req.mPostID, upCount, req.mUsername);
                count += 1;
                update = db.updateDownVotes(req.mPostID, count, req.mUsername);
            } else if (vote.equals("Downvote")) {
                count -= 1;
                update = db.updateDownVotes(req.mPostID, count, req.mUsername);
                return gson.toJson(new StructuredResponse("ok", null, update));
            } else {
                count += 1;
                update = db.updateDownVotes(req.mPostID, count, req.mUsername);
            }

            if (!update) {
                return gson.toJson(new StructuredResponse("error", "unable to update row ", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, update));
            }
        });
    }

    /**
     * POST route for adding a new comment to a post. This will read
     * JSON from the body of the request, turn it into a SimpleRequest
     * object, extract the title and message, insert them, and return the
     * ID of the newly created row.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void newComment(Gson gson, Database db) {
        Spark.post("/addComment", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            CommentData req = gson.fromJson(request.body(), CommentData.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = db.insertComment(req.mP_ID, req.mUsername, req.mContent);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
    }

    /**
     * PUT route for updating a comment on a post.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void updateComment(Gson gson, Database db) {
        Spark.put("/addComment/:id", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            CommentData req = gson.fromJson(request.body(), CommentData.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = db.updateComment(req.mContent, req.mId);

            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
    }

    /**
     * GET route that returns the content of a specific comment
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getCommentByID(Gson gson, Database db) {
        Spark.get("/comment/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            String data = db.selectCommentByID(idx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * GET route that returns the content of the comments by a given user
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getCommentByUsername(Gson gson, Database db) {
        Spark.get("/commentByUsername/:username", (request, response) -> {
            String username = request.params("username");
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            ArrayList<String> data = db.selectCommentByUsername(username);
            if (data.size() == 0) {
                return gson.toJson(new StructuredResponse("error", "; comment not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * GET route that returns the content of a specific comment
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getCommentByPost(Gson gson, Database db) {
        Spark.get("/commentByPost/:pId", (request, response) -> {
            int idx = Integer.parseInt(request.params("pId"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            ArrayList<String> data = db.selectCommentByPost(idx);
            if (data.size() == 0) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * GET route that returns number of upVotes on a post
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getUpVoteByPost(Gson gson, Database db) {
        Spark.get("/upVote/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int data = db.selectUpVotesByID(idx);
            if (data == -1) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * GET route that returns number of upVotes on a post
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getDownVoteByPost(Gson gson, Database db) {
        Spark.get("/downVote/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int data = db.selectDownVotesByID(idx);
            if (data == -1) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * GET route that returns the last vote from a user on a specifc post
     * The ":id" suffix in the first parameter to get() becomes
     * request.params("id"), so that we can get the requested Post ID. If
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error. Otherwise, we have an integer, and the only possible
     * error is that it doesn't correspond to a Post with data.
     * 
     * @param gson GSON Object
     * @param db   Database Object
     * 
     *             return JSON Object with data described above
     */
    public static void getLatestVote(Gson gson, Database db) {
        Spark.get("/latestVote/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            VotesRequest req = gson.fromJson(request.body(), VotesRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            String data = db.selectMostRecentVote(idx, req.mUsername);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });
    }

    /**
     * Get an integer environment variable if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     *             returns The best answer we could come up with for a value for
     *             envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
     * 
     * PHASE 3 GOOGLE SERVICE ACCOUNT STUFF
     */
    public static class MyCredentials {
        public static void Credentials(String[] args) throws IOException {
                GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("power-383304-0069cfca7f9f.json"));
                Storage storage = StorageOptions.newBuilder()   //creates a storage object usig StorageOptions class and passing credentials
                .setCredentials(credentials)
                .build().getService();

                String bucketName = "my_bucket";
                Bucket bucket = storage.create(BucketInfo.newBuilder(bucketName).build());

                BlobId blobId = BlobId.of(bucketName, "/file.txt");
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                Blob blob = storage.create(blobInfo, "Hello, World!".getBytes());

        }
    }
  

    /**
     * Google Authentication of User
     * 
     * @param gson          gson object
     * @param db            database
     * @param idTokenString user's id token
     * @param random        randomizer
     * @param userStore     hashmap
     * @param CLIENT_ID     clientID
     * @return a string for session id
     */
    public static String authenticateUser(Gson gson, Database db, String idTokenString, Random random,
            HashMap<String, Integer> userStore, String CLIENT_ID) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                    new GsonFactory())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID)).build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Get profile information from payload
                String email = idToken.getPayload().getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                String userId = payload.getSubject();

                // Be sure users email ends in @lehigh.edu
                if (!(email.substring(email.length() - 11, email.length()).equals("@lehigh.edu"))) {
                    return "Invalid Domain";
                }

                // Check if user already exists in the database
                boolean userFound = db.checkUser(name);

                if (!userFound) { // If user is not found, create user
                    Integer newRandomSessionKey; // New random session key for new user

                    // generate unique session key
                    do {
                        newRandomSessionKey = random.nextInt(1000000);
                        if (userStore.isEmpty()) {
                            break;
                        } else {
                            if (userStore.containsValue(newRandomSessionKey)) {
                                continue;
                            } else {
                                break;
                            }
                        }
                    } while (true);

                    // Now create new user
                    db.insertUser(name, email, "default", "default", "default");

                    UserInfo newUserInfo = new UserInfo(name, 1, email, Integer.toString(newRandomSessionKey));

                    userStore.put(email, newRandomSessionKey);

                    return gson.toJson(newUserInfo); // Return the VerificationUserID and VerificaitonSessionKey in a
                                                     // structured response

                } else {

                    Integer newRandomSessionKey; // New random session key for user

                    // generate unique session key
                    boolean unique = false;
                    do {
                        newRandomSessionKey = random.nextInt(1000000);
                        if (userStore.isEmpty()) {
                            break;
                        } else {
                            if (userStore.containsValue(newRandomSessionKey)) {
                                continue;
                            } else {
                                break;
                            }
                        }
                    } while (true);

                    UserInfo newUserInfo = new UserInfo(name, 1, email, Integer.toString(newRandomSessionKey));

                    userStore.put(email, newRandomSessionKey);

                    return gson.toJson(newUserInfo); // Return the VerificationUserID and VerificaitonSessionKey in a
                                                     // structured response
                }
            } else {
                System.out.println("Invalid ID token.");
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        } catch (IOException e) {
            String error_message = e.getMessage();
        } catch (GeneralSecurityException e) {
            String error_message = e.getMessage();
        }
        return null;
    }

    /**
     * Route for Post authenticate
     * 
     * @param gson      gson object
     * @param db        database
     * @param random    random number
     * @param userStore hashmap to save user and client ID
     * @param CLIENT_ID client ID
     */
    public static void authenticate(Gson gson, Database db, Random random, HashMap<String, Integer> userStore,
            String CLIENT_ID) {
        Spark.post("/authenticate", (request, response) -> {
            GoogleTokenFE idTokenString = gson.fromJson(request.body(), GoogleTokenFE.class);
            // response.status(200);
            response.type("application/json");

            String UserInformation = authenticateUser(gson, db, idTokenString.token, random, userStore, CLIENT_ID);

            if (UserInformation == null) {
                return gson.toJson(new StructuredResponse("error", "error authenticating user", null));
            } else {
                return gson.toJson(UserInformation);
            }
        });
    }

}