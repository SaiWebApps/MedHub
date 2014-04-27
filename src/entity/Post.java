package entity;

/**
 * Post object, represents a user post/message.
 * @author Sairam Krishnan (sbkrishn)
 */
public class Post {
	private long postId;
	private long userId;
	private String timestamp;
	private String postText;
	private int numViews;
	
	/**
	 * @return the id of the post
	 */
	public long getPostId() { return postId; }
	/**
	 * Set the id of the post.
	 * @param postId - New id for the post 
	 */
	public void setPostId(long postId) { this.postId = postId; }
	
	/**
	 * @return the id of the user who posted this message
	 */
	public long getUserId() { return userId; }
	/**
	 * Keep track of this post's author.
	 * @param userId - Id of the user who posted this 
	 */
	public void setUserId(long userId) { this.userId = userId; }
	
	/**
	 * @return the time at which this post was posted
	 */
	public String getTimestamp() { return timestamp; }
	/**
	 * Set the time at which this post was posted.
	 * @param timestamp - when this post was posted
	 */
	public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
	
	public String getPostText() { return postText; }
	/**
	 * Set the text/message of the user's post.
	 * @param postText - Message/content of user's post
	 */
	public void setPostText(String postText) { this.postText = postText; }
	
	/**
	 * @return the number of views that this post has gotten
	 */
	public int getNumViews() { return numViews; }
	/**
	 * Set the number of views that this post has gotten.
	 * @param numViews - Number of views received by this post so far
	 */
	public void setNumViews(int numViews) { this.numViews = numViews; }
}