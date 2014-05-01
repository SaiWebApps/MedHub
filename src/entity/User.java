package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Generic user object, contains general information about a user.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class User implements Parcelable {
	private long userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private int type;
	private int score;
	
	public User() {}
	
	public User(Parcel in) {
		userId = in.readLong();
		email = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		type = in.readInt();
		score = in.readInt();
	}
	
	/**
	 * @return the user id
	 */
	public long getUserId() { return userId; }
	/**
	 * Set the user id.
	 * @param userId - New id for user
	 */
	public void setUserId(long userId) { this.userId = userId; }

	/**
	 * @return the user email
	 */
	public String getEmail() { return email; }
	/**
	 * Set the user email.
	 * @param email - New email for user
	 */
	public void setEmail(String email) { this.email = email; }

	/**
	 * @return the user password
	 */
	public String getPassword() { return password; }
	/**
	 * Set the password for the user.
	 * @param password - New password for user
	 */
	public void setPassword(String password) { this.password = password; }

	/**
	 * @return the user's first name
	 */
	public String getFirstName() { return firstName; }
	/**
	 * Set the first name of the user.
	 * @param firstName - New first name for the user
	 */
	public void setFirstName(String firstName) { this.firstName = firstName; }

	/**
	 * @return the user's last name
	 */
	public String getLastName() { return lastName; }
	/**
	 * Set the last name of the user.
	 * @param lastName - New last name for the user
	 */
	public void setLastName(String lastName) { this.lastName = lastName; }

	/**
	 * @return 0 if the user is a doctor, 1 if the user is a patient (not a doctor)
	 */
	public int getType() { return type; }
	/**
	 * Set the type of user.
	 * @param type - Type of user (0 for doctor, 1 for non-doctor)
	 */
	public void setType(int type) { this.type = type; }

	/**
	 * @return this user's score (determined by quality of responses)
	 */
	public int getScore() { return score; }
	/**
	 * Set this user's score.
	 * @param score - New score for the user
	 */
	public void setScore(int score) { this.score = score; }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(userId);
		dest.writeString(email);
		dest.writeString(password);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeInt(type);
		dest.writeInt(score);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
}