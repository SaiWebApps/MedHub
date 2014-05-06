package ws.local;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Register accelerometer, pressure, and gyroscope sensors.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class SensorService implements SensorEventListener {
	private SensorClient client;
	private SensorManager sm;
	
	/**
	 * Register a SensorClient.
	 * @param client - Client using this SensorService
	 */
	public SensorService(SensorClient client) {
		this.client = client;
	}
	
	/**
	 * Register the sensors supported by this service - accelerometer,
	 * gyroscope, and pressure sensors.
	 * @param a - Activity used to register sensors; supposed to be client
	 */
	public void registerSensors(Activity a) {
		sm = (SensorManager) a.getSystemService(Context.SENSOR_SERVICE);
		Sensor acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor pressure = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
		Sensor g = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		sm.registerListener(this, acc , SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, g, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void deregister() {
		sm.unregisterListener(this);
	}
	
	/**
	 * Whenever a registered sensor changes, this event is triggered.
	 * In any case, figure out which sensor was triggered and pass on
	 * the sensor info to the client, which will deal with the actual
	 * business logic of using that info.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;

		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			client.handleAccelerometer(event.values);
		}
		else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
			client.handlePressure(event.values);
		}
		else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			client.handleGyroscope(event.values);
		}		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
