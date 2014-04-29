package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Doctor object, contains information specific to a doctor.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Doctor implements Parcelable {
	private long doctorId;
	private long userId;
	private String credentials;
	
	public Doctor() {}
	
	public Doctor(Parcel in) {
		doctorId = in.readLong();
		userId = in.readLong();
		credentials = in.readString();
	}
	
	/**
	 * @return the doctor id
	 */
	public long getDoctorId() { return doctorId; }
	/**
	 * Set the doctor id.
	 * @param doctorId - New id for the doctor
	 */
	public void setDoctorId(long doctorId) { this.doctorId = doctorId; }
	
	/**
	 * @return the user id, to find the doctor in the user table
	 */
	public long getUserId() { return userId; }
	/**
	 * Set the user id. The user id is a foreign key that links to the
	 * user table.
	 * @param userId - New user id for the doctor
	 */
	public void setUserId(long userId) { this.userId = userId; }
	
	/**
	 * @return the doctor's credentials
	 */
	public String getCredentials() { return credentials; }
	/**
	 * Set the doctor's credentials.
	 * @param credentials - Doctor's credentials
	 */
	public void setCredentials(String credentials) { this.credentials = credentials; }
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(doctorId);
		dest.writeLong(userId);
		dest.writeString(credentials);
	}
	
	public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
		public Doctor createFromParcel(Parcel in) {
			return new Doctor(in);
		}

		public Doctor[] newArray(int size) {
			return new Doctor[size];
		}
	};
}