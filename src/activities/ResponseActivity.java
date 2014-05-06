package activities;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Post;
import entities.Response;
import entities.User;
import fragment.ResponseFragment;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Processes user responses to posts.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class ResponseActivity extends ActionBarActivity {
	private DatabaseManager dbm = new DatabaseManager(ResponseActivity.this);
	private ResponseFragment rf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities_response);

		if (savedInstanceState == null) {
			rf = new ResponseFragment();
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, rf).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.response, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Pull up an AlertDialog with a textfield to allow user to respond
	 * to posts/queries.
	 * @param v - Button to request response creation
	 */
	public void getResponseForm(View v) {
		LayoutInflater inflater = getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final View inside = inflater.inflate(R.layout.template_create_response, null);
		
		builder.setView(inside)
		.setPositiveButton("Create Response", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				EditText response = (EditText) inside.findViewById(R.id.response);
				String rText = response.getText().toString();
				Post p = (Post) getIntent().getParcelableExtra("post");
				User u = (User) getIntent().getParcelableExtra("user");
				
				//Create response object using user inputs.
				Response r = new Response();
				r.setPostId(p.getPostId());
				r.setUserId(u.getUserId());
				r.setResponseText(rText);
				r.setVotes(0);
				
				//Create response.
				dbm.open();
				dbm.createResponse(r);
				dbm.close();
				
				rf.updateResponseList();
				dialog.dismiss();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
}