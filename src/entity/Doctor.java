package entity;

/**
 * Doctor object, contains information specific to a doctor.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Doctor {
	private long doctorId;
	private long userId;
	private String credentials;
	
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
}