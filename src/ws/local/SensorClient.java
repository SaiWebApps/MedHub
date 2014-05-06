package ws.local;

/**
 * Responsibilities to be satisfied by any client of our SensorService.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public interface SensorClient {

	/**
	 * Handle the information from the accelerometer.
	 * @param values - (x,y,z) coordinates from the accelerometer
	 */
	public void handleAccelerometer(float[] values);
	
	/**
	 * Handle the gyroscope's info.
	 * @param values - Info from the gyroscope
	 */
	public void handleGyroscope(float[] values);
	
	/**
	 * Handle pressure sensor's info.
	 * @param values - Info from pressure sensor
	 */
	public void handlePressure(float[] values);
}