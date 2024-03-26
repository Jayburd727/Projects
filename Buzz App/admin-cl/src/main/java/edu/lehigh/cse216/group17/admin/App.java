package edu.lehigh.cse216.group17.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;


/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [U] Create User table");
        System.out.println("  [P] Create Post table");
        System.out.println("  [B] Add Validation Column to User Table");
        System.out.println("  [D] Remove Validation Column to User Table");
        System.out.println("  [T] Add Column to Post Table");
        System.out.println("  [V] Remove Validation Column to Post Table");
        System.out.println("  [C] Create Comment table");
        System.out.println("  [A] Create Action table");
        System.out.println("  [F] Create File table");
        System.out.println("  [O] Drop Files Table");
        System.out.println("  [J] Add File ID Column to Post Table");
        System.out.println("  [K] Add File ID Column to Comment Table");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [E] Query for all rows");
        System.out.println("  [-] Delete post");
        System.out.println("  [Z] Delete user");
        System.out.println("  [X] Delete file");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [S] Insert a new file");
        System.out.println("  [~] Update a row");
        System.out.println("  [#] Update User Validation Status");
        System.out.println("  [!] Update Post Validation Status");
        System.out.println("  [$] Update File Validation Status");
        System.out.println("  [L] List Documents");
        System.out.println("  [N] Get File ID's of Documents ascending order of date accessed");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "UPBDTVCAFOJK1E-ZX+S~#!$LNq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided. May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided. On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options. Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");


        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(ip, port, user, pass);
        if (db == null)
            return;

      //  PostData d = new PostData();
       // int a = d.mId;
        

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            // function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'B') {
                 db.insertUserColumn();
            } else if (action == 'D') {
                 db.deleteUserColumn();
            } else if (action == 'O') {
                db.dropTable();
            }else if (action == 'T') {
                db.insertPostColumn();
            } else if (action == 'V') {
                db.deletePostColumn();
            } 
            else if (action == 'U') {
                db.createUserTable();
            }
            else if (action == 'P') {
                db.createPostTable();
            }
            else if (action == 'C') {
                db.createCommentTable();
            }
            else if (action == 'A') {
                db.createActionTable();
            } else if (action == 'F') {
                db.createFileTable();
            } else if (action == 'J') {
                db.insertFileColumnToPost();
            } else if (action == 'K') {
                db.insertFileColumnToComment();
            } else if (action == '1') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                PostData res = db.selectOne(id);
                if (res != null) {
                    System.out.println("  [" + res.mId + "] " + res.mTitle);
                    System.out.println("  --> " + res.mContent);
                }
            }  else if (action == '-') {
                int id = getInt(in, "Enter the post ID");
                if (id == -1)
                    continue;
                int res = db.deleteOne(id);
                if (res == -1)
                    continue;
                System.out.println(" Post with id " + id + " deleted");
            } else if (action == 'X') {
                String file_id = getString(in, "Enter the file ID");
                int res = db.deleteFile(file_id);
                if (res == -1)
                    continue;
                System.out.println(" File with id " + file_id + " deleted");
            } else if (action == 'Z') {
                String email = getString(in, "Enter the user email");
                if (email == "")
                    continue;
                int res = db.deleteUser(email);
                if (res == -1)
                    continue;
                System.out.println(" User with email " + email + " deleted");
            }else if (action == '+') {
                String username = getString(in, "Enter the username");
                String subject = getString(in, "Enter the subject");
                String message = getString(in, "Enter the message");
                if (subject.equals("") || message.equals(""))
                    continue;
                int res = db.insertRow(username,subject, message);
                System.out.println(res + " rows added");
            } else if (action == 'S') {
                String URL = getString(in, "Enter the URL");
                int Post_ID = getInt(in, "Enter the post id");
                int Comment_ID = getInt(in, "Enter the comment id");
                String username = getString(in, "Enter the username");
                if (URL.equals("") || username.equals(""))
                    continue;
                int res = db.insertFile(URL,Post_ID, Comment_ID,username);
                System.out.println(res + " rows added");
            }
            else if (action == '~') {
                int id = getInt(in, "Enter the row ID :> ");
                if (id == -1)
                    continue;
                String newMessage = getString(in, "Enter the new message");
                String newContent = getString(in, "Enter the new content");
                int res = db.updateOne(id, newMessage, newContent);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            } else if (action == '#') {
                String username = getString(in, "Enter the Username :> ");
                if (username == " ")
                    continue;
                String newStatus = getString(in, "Enter the new status");
                int res = db.updateUserValidation(username, newStatus);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            } else if (action == '!') {
                int post_ID = getInt(in, "Enter the Post ID :> ");
                if (post_ID == -1)
                    continue;
                String newStatus = getString(in, "Enter the new status");
                int res = db.updatePostValidation(post_ID, newStatus);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            } else if (action == '$') {
                String file_ID = getString(in, "Enter the File ID :> ");
                String newStatus = getString(in, "Enter the new status");
                int res = db.updateFileValidationStatus(file_ID, newStatus);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            } else if (action== 'L'){
                try {
                    GoogleDrive.main();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (GeneralSecurityException p){
                    p.printStackTrace();
                }
            } else if (action=='N'){
                db.orderFilesByDateAccessed();
            }
        }
        // Always remember to disconnect from the database when the program
        // exits
        db.disconnect();
    }
}
