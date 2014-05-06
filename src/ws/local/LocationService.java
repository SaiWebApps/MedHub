package ws.local;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Track user's current location.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class LocationService {
	private LocationManager lm;
	private LocationListener lc;
	private Toast t;
	
	public LocationService(Activity a) {
		lm = (LocationManager) a.getSystemService(Context.LOCATION_SERVICE);
		createLocListener(a);
	}
	
	/**
	 * Register location listener.
	 * @param a - Activity tracking location
	 */
	public void createLocListener(final Activity a) {
		lc = new LocationListener() {

			@Override
			public void onLocationChanged(Location newLoc) {
				//Display toast with user's current location.
				t = Toast.makeText(a.getApplicationContext(), 
						"Latitude: " + newLoc.getLatitude() + ", Longitude: " +
								newLoc.getLongitude(), Toast.LENGTH_LONG);
				t.setGravity(Gravity.TOP, Gravity.CENTER, Gravity.CENTER);
				t.getView().setBackgroundColor(Color.DKGRAY);
				t.show();
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
	}
	
	/**
	 * Request location updates from multiple sources.
	 */
	public void requestUpdates() {
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lc);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lc);
	}
	
	public void destroy() {
		if (t != null) {
			t.cancel();
		}
	}
}
