package ws.local;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Allows users to send SMS via Android phones.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class SMSService {

	public SMSService() {}
	
	/**
	 * Send SMS with the given message to the given phone number.
	 * Display a toast indicating whether we were able to send the SMS
	 * successfully.
	 * @param a - Activity that is requesting to send SMS
	 * @param phoneNumber - Phone number to send SMS to
	 * @param message - SMS message/contents
	 */
	public void sendSMS(Activity a, String phoneNumber, String message) {
		try {
			SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
			Toast.makeText(a.getApplicationContext(), "Successfully sent SMS",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(a.getApplicationContext(), "Failed to send SMS", 
					Toast.LENGTH_SHORT).show();
		}
	}
}