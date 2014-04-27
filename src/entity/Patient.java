package entity;

/**
 * Patient object, contains info specific to a patient/non-doctor user.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class Patient {
	private long patientId;
	private long userId;
	private String medicalBio;
	
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
}