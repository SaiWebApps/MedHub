package entry.handlers;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import android.app.Activity;
import android.widget.EditText;

public class LoginHandler {
	private EditText loginEmailView;
	private EditText loginPasswordView;
	
	private String loginEmail;
	private String loginPassword;
	
	private List<String> errorMessages = new ArrayList<String>();
	private DatabaseManager dbm;
	
	public LoginHandler(Activity activity, DatabaseManager dbm) {
		this.loginEmailView = (EditText) activity.findViewById(R.id.loginEmail);
		this.loginPasswordView = (EditText) activity.findViewById(R.id.loginPassword);
		this.loginEmail = loginEmailView.getText().toString().trim();
		this.loginPassword = loginPasswordView.getText().toString().trim();
		this.dbm = dbm;
	}
	
	private void validateFields() {
		if (loginEmail.isEmpty()) {
			
		}
	}
	
	public void resetFields() {
		
	}
	
	
	public void login() {
		
	}
}