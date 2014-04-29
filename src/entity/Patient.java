package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Patient object, contains info specific to a patient/non-doctor user.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Patient implements Parcelable {
	private long patientId;
	private long userId;
	private String medicalBio;
	
	public Patient() {}
	
	public Patient(Parcel in) {
		patientId = in.readLong();
		userId = in.readLong();
		medicalBio = in.readString();
	}
	
	/**
	 * @return the patient id
	 */
	public long getPatientId() { return patientId; }
	/**
	 * Set the patient id.
	 * @param patientId - New id for the patient
	 */
	public void setPatientId(long patientId) { this.patientId = patientId; }
	
	/**
	 * @return the user id, to find the patient in the user table
	 */
	public long getUserId() { return userId; }
	/**
	 * Set the user id. The user id is a foreign key that links to the
	 * user table.
	 * @param userId - New user id for the patient
	 */
	public void setUserId(long userId) { this.userId = userId; }
	
	/**
	 * @return the patient's medical bio
	 */
	public String getMedicalBio() { return medicalBio; }
	/**
	 * Set the patient's medical bio.
	 * @param medicalBio - Patient's medicalBio
	 */
	public void setMedicalBio(String medicalBio) { this.medicalBio = medicalBio; }
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(patientId);
		dest.writeLong(userId);
		dest.writeString(medicalBio);
	}
	
	public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
		public Patient createFromParcel(Parcel in) {
			return new Patient(in);
		}

		public Patient[] newArray(int size) {
			return new Patient[size];
		}
	};
}