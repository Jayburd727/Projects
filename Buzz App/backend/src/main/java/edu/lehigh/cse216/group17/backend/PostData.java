package edu.lehigh.cse216.group17.backend;
import java.sql.Timestamp;
import java.util.Date;

/**
 * PostData holds a row of information of a post.  A row of information consists of
 * an identifier, strings for a "title" and "content", a creation date, and number of likes.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class PostData {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;

    /**
     * The title for this row of data
     */
    public String mTitle;

    /**
     * The content for this row of data
     */
    public String mContent;

    /**
     * The creation timestamp for this row of data.  Once it is set, it cannot be 
     * changed
     */
    public final Timestamp mCreated;

    /**
     * The number of up votes of the post
     */
    public int mUpVote;

    /**
     * The number of down votes of the post
     */
    public int mDownVote;

    /**
     * The Username of the user
     */
    public String mUsername;


    /**
     * Create a new PostData with the provided id and title/content, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param title The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param created Timestamp in EST for when the post was created
     * 
     * @param upVotes Number of up votes the post has
     * 
     * @param downVotes Number of down Votes the post has
     * 
     * @param username username of the user
     * 
     */
    public PostData(int id, String title, String content, Timestamp created, int upVotes, int downVotes, String username) {
        mId = id;
        mTitle = title;
        mContent = content;
        mCreated = created;
        mUpVote=upVotes;
        mDownVote=downVotes;
        mUsername=username;
    }

    /**
     * Copy constructor to create one PostData from another
     */
    PostData(PostData data) {
        mId = data.mId;
        // NB: Strings and TimeStamps are immutable, so copy-by-reference is safe
        mTitle = data.mTitle;
        mContent = data.mContent;
        mCreated = data.mCreated;
        mDownVote=data.mDownVote;
        mUpVote=data.mUpVote;
        mUsername=data.mUsername;
    }
}