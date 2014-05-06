package adapters;

import java.util.List;

import edu.cmu.medhub.R;
import entities.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Display a given list of posts in a custom list view.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class PostAdapter extends BaseAdapter {
	private Context context;
	private List<Post> postList;

	/**
	 * @constructor
	 * @param context - App context
	 * @param postList - List of posts
	 */
	public PostAdapter(Context context, List<Post> postList) {
		this.context = context;
		this.postList = postList;
	}

	@Override
	public int getCount() {
		if (postList == null) {
			return 0;
		}
		return postList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < 0 || position >= postList.size()) {
			return null;
		}
		return postList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * For each item in the list of posts, display the post details.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.template_post_listitem, parent, false);
		
		//Get the right post, and all of the views in the list item.
		Post post = postList.get(position);
		TextView postTitle = (TextView) convertView.findViewById(R.id.postItemTitle);
		TextView time = (TextView) convertView.findViewById(R.id.postItemTimestamp);
		TextView postText = (TextView) convertView.findViewById(R.id.postItemText);
		TextView postId = (TextView) convertView.findViewById(R.id.postItemId);
		
		//Set views to post details.
		postTitle.setText(post.getPostTitle());
		time.setText(post.getTimestamp());
		postText.setText(post.getPostText());
		postId.setText("" + post.getPostId());
		
		return convertView;
	}
}