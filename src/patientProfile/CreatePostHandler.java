package patientProfile;

import dbLayout.DatabaseManager;
import baseActivity.handler.ActivityHandler;
import edu.cmu.medhub.R;
import entity.Post;
import entity.User;
import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePostHandler extends ActivityHandler {
	private EditText postTitleView;
	private EditText postTextView;
	private TextView postErrors;
	private String postTitle;
	private String postText;
	private DatabaseManager dbm;
	private long userId;
	
	public CreatePostHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.postTitleView = (EditText) a.findViewById(R.id.postTitle);
		this.postTextView = (EditText) a.findViewById(R.id.postText);
		this.postErrors = (TextView) a.findViewById(R.id.postErrors);
		this.postTitle = postTitleView.getText().toString().trim();
		this.postText = postTextView.getText().toString().trim();
		this.userId = ((User) a.getIntent().getParcelableExtra("user")).getUserId();
	}

	@Override
	public boolean validateFields() {
		if (postTitle.isEmpty()) {
			errorMessages.add("Post title cannot be empty.");
		}
		if (postText.isEmpty()) {
			errorMessages.add("Post text cannot be empty.");
		}
		return (errorMessages.size() == 0);
	}
	
	public void clearFields() {
		postErrors.setText("");
		postTitleView.setText("");
		postTextView.setText("");
	}
	
	public void createPost() {
		if (!validateFields()) {
			displayErrorMessages(postErrors);
			return;
		}
		dbm.open();
		Post p = new Post();
		p.setUserId(userId);
		p.setPostText(postText);
		p.setPostTitle(postTitle);
		dbm.createPost(p);
		dbm.close();
		clearFields();
		Log.v("Post Creation Status", "Created post successfully");
	}
}