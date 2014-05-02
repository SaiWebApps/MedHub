package patientProfile;

import java.util.List;
import edu.cmu.medhub.R;
import entity.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter {
	private Context context;
	private List<Post> postList;

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listitem, parent, false);
		
		Post post = postList.get(position);
		TextView postTitle = (TextView) convertView.findViewById(R.id.postItemTitle);
		TextView numViews = (TextView) convertView.findViewById(R.id.postItemNumViews);
		TextView time = (TextView) convertView.findViewById(R.id.postItemTimestamp);
		
		postTitle.setText(post.getPostTitle());
		numViews.setText("" + post.getNumViews());
		time.setText(post.getTimestamp());
		
		return convertView;
	}
}