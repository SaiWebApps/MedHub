package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Post;
import entities.User;
import activities.ResponseActivity;
import adapters.PostAdapter;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AllPostsHandler extends ActivityHandler {	
	private ListView allPosts;
	private DatabaseManager dbm;

	public AllPostsHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.allPosts = (ListView) a.findViewById(R.id.allPosts);
		attachListener(a);
		updatePostList(a);
	}

	public void attachListener(final Activity a) {
		allPosts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				RelativeLayout rl = (RelativeLayout) view;
				TextView postId = (TextView) rl.findViewById(R.id.postItemId);	
				String pid = postId.getText().toString();
				User u = (User) a.getIntent().getParcelableExtra("user");
				Post p = dbm.getPost(Integer.parseInt(pid));
				
				Intent showResponses = new Intent(a.getApplicationContext(), ResponseActivity.class);
				showResponses.putExtra("post", p);
				showResponses.putExtra("user", u);
				a.startActivity(showResponses);
			}
		});
	}

	public void updatePostList(Activity a) {
		PostAdapter adapter = new PostAdapter(a.getApplicationContext(), dbm.getAllPosts());
		allPosts.setAdapter(adapter);
	}

	@Override
	public boolean validateFields() {
		return false;
	}
}