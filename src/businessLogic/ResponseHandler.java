package businessLogic;

import java.util.List;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Post;
import entities.Response;
import adapters.ResponseAdapter;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

public class ResponseHandler extends ActivityHandler {	
	private TextView queryTitle;
	private TextView queryText;
	private ListView allResponses;
	private DatabaseManager dbm;

	public ResponseHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.queryTitle = (TextView) a.findViewById(R.id.queryTitle);
		this.queryText = (TextView) a.findViewById(R.id.queryText);
		this.allResponses = (ListView) a.findViewById(R.id.allResponses);
		updateResponseList(a);
	}
	
	public void updateResponseList(Activity a) {
		Post p = (Post) a.getIntent().getParcelableExtra("post");
		queryTitle.setText(p.getPostTitle());
		queryText.setText(p.getPostText());
		
		List<Response> responses = dbm.getAllResponses(p.getPostId());
		ResponseAdapter rad = new ResponseAdapter(a.getApplicationContext(), responses);
		allResponses.setAdapter(rad);
	}
	
	@Override
	public boolean validateFields() {
		return false;
	}
}