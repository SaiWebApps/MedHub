package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Post object, represents a user post/message.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Post implements Parcelable {
	private long postId;
	private long userId;
	private String timestamp;
	private String postTitle;
	private String postText;
	
	public Post() {}
	
	public Post(Parcel in) {
		postId = in.readLong();
		userId = in.readLong();
		timestamp = in.readString();
		postTitle = in.readString();
		postText = in.readString();
	}
	
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
	 * @return this post's title.
	 */
	public String getPostTitle() { return postTitle; }
	
	/**
	 * Set this post's title.
	 * @param postTitle - Title of this post
	 */
	public void setPostTitle(String postTitle) { this.postTitle = postTitle; }

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(postId);
		dest.writeLong(userId);		
		dest.writeString(timestamp);
		dest.writeString(postTitle);
		dest.writeString(postText);
	}
	
	public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
		public Post createFromParcel(Parcel in) {
			return new Post(in);
		}

		public Post[] newArray(int size) {
			return new Post[size];
		}
	};

	@Override
	public boolean equals(Object obj) {
		Post p = (Post) obj;
		return p.userId == userId && p.timestamp == timestamp && p.postText == p.postText;
	}
}