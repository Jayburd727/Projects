package edu.lehigh.cse216.group17.admin;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Comment holds a row of information of a comment.  A row of information consists of
 * an identifier, the post ID, "content", a creation date, and the username of the person who commented.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class CommentData {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;

    /**
     * The post ID the comment is made on
     */
    public int mP_ID;

    /**
     * The content for this comment
     */
    public String mContent;

    /**
     * The creation timestamp for this row of data.  Once it is set, it cannot be 
     * changed
     */
    public final Timestamp mCreated;

    /**
     * The username of the commenter
     */
    public String mUsername;


    /**
     * Create a new CommentData with the provided id and title/content, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param P_ID The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param created Timestamp in EST for when the post was created
     * 
     * @param username username of commenter
     * 
     */
    public CommentData(int id, int P_ID, String content, Timestamp created, String username) {
        mId = id;
        mP_ID = P_ID;
        mContent = content;
        mCreated = created;
        mUsername=username;
    }

    /**
     * Copy constructor to create one Comment Data from another
     */
    CommentData(CommentData data){
        mId = data.mId;
        // NB: Strings and TimeStamps are immutable, so copy-by-reference is safe
        mP_ID = data.mP_ID;
        mContent = data.mContent;
        mCreated = data.mCreated;
        mUsername=data.mUsername;
    }
}