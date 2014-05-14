package ws.local;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cmu.medhub.R;

public class MapService implements ConnectionCallbacks, OnConnectionFailedListener {
	private Activity mapActivity;
	private GoogleMap googleMap;
	private LocationClient locationClient;
	private Geocoder g;
	private Location currentLoc;
	private MarkerOptions marker;
	
	/**
	 * Extracts a map fragment from the given MapActivity, and creates a
	 * GoogleMap object and initializes location services.
	 * @param mapActivity
	 */
	public MapService(Activity mapActivity) {
		this.mapActivity = mapActivity;
		createMap();
		createLocationServices();
	}

	/**
	 * Create a GoogleMap object, which corresponds to the map fragment
	 * in the map activity.
	 */
	private void createMap() {
		googleMap = ((MapFragment) mapActivity.getFragmentManager().
				findFragmentById(R.id.map)).getMap();
		googleMap.setMyLocationEnabled(true);
		System.gc();
	}

	/**
	 * Initialize a LocationClient and open a connection.
	 */
	private void createLocationServices() {
		g = new Geocoder(mapActivity.getApplicationContext(), Locale.getDefault());
		locationClient = new LocationClient(mapActivity.getApplicationContext(),this,this);
		locationClient.connect();
		System.gc();
	}

	/**
	 * @return the current location of the user, using an open LocationClient connection.
	 * @throws IOException
	 */
	private Address getCurrentLocation() throws IOException {
		currentLoc = locationClient.getLastLocation();
		List<Address> addresses = g.getFromLocation(currentLoc.getLatitude(), 
				currentLoc.getLongitude(), 1);
		if (addresses.isEmpty()) {
			return null;
		}
		return addresses.get(0);
	}

	/**
	 * Focus the camera on current location, and zoom in on it.
	 */
	private void zoomIntoCurrentLocation() {
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
				new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude()))
				.zoom(15).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	/**
	 * Release all resources used by this MapService. Disconnects LocationClient
	 * if it hasn't been disconnected already.
	 */
	public void releaseResources() {
		googleMap = null;		
		g = null;
		mapActivity = null;
		if (locationClient.isConnected()) {
			locationClient.disconnect();
		}
		locationClient = null;
	}

	/**
	 * Create a marker at the current location with the loc's city name (locality)
	 * and postal code.
	 */
	private void setLocationMarker(String title) {
		if (marker != null) {
			return;
		}
		LatLng l = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
		marker = new MarkerOptions().position(l).title(title);
		googleMap.addMarker(marker);
	}

	//Callback handlers
	/**
	 * Upon successfully connecting to Google Play Services, the LocationClient
	 * will enter this method. It will print a log message indicating success
	 * and then display a toast with the user's current location/city.
	 */
	@Override
	public void onConnected(Bundle arg0) {
		Log.v("Location Client Status", "Connected");
		Address a;
		try {
			a = getCurrentLocation();
			setLocationMarker(a.getLocality() + " " + a.getPostalCode());
			zoomIntoCurrentLocation();
		} catch (IOException e) {
			Log.v("Map Services Error", "Unable to display current location");
		}
	}

	@Override
	public void onDisconnected() {
		Log.v("Location Client Status", "Disconnected");
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.v("Location Client Status", "Error - Unable to connect");
	}
}