package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Response object, represents a user response to a post.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Response implements Parcelable {
	private long responseId;
	private long postId;
	private long userId;
	private String timestamp;
	private String responseText;
	private int votes;
	
	public Response() {}
	
	public Response(Parcel in) {
		responseId = in.readLong();
		postId = in.readLong();
		userId = in.readLong();
		timestamp = in.readString();
		responseText = in.readString();
		votes = in.readInt();
	}
	
	/* Getters and setters for primary key, responseId */
	public long getResponseId() { return responseId; }
	public void setResponseId(long responseId) { this.responseId = responseId; }
	
	/* Getters and setters for id of post that this response was created for */
	public long getPostId() { return postId; }
	public void setPostId(long postId) { this.postId = postId; }
	
	/* Getters and setters for the id of the user who created this response */
	public long getUserId() { return userId; }
	public void setUserId(long userId) { this.userId = userId; }
	
	/* Getters and setters for the time at which this response was created */
	public String getTimestamp() { return timestamp; }
	public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
	
	/* Getters and setters for the text within the response */
	public String getResponseText() { return responseText; }
	public void setResponseText(String responseText) { this.responseText = responseText; }
	
	/* Getters and setters for the number of votes received by this response */
	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(responseId);
		dest.writeLong(postId);
		dest.writeLong(userId);		
		dest.writeString(timestamp);
		dest.writeString(responseText);
		dest.writeInt(votes);
	}
	
	public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
		public Response createFromParcel(Parcel in) {
			return new Response(in);
		}

		public Response[] newArray(int size) {
			return new Response[size];
		}
	};
}