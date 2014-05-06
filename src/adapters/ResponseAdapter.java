package adapters;

import java.util.List;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Response;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Display each response to a post in a list. Show the response message,
 * timestamp, and upvote/downvote buttons. Also, handle upvoting and downvoting.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class ResponseAdapter extends BaseAdapter {
	private Context context;
	private List<Response> responseList;
	private DatabaseManager dbm;
	
	/**
	 * @constructor
	 * @param context - Application context
	 * @param responseList - List of Responses to given post
	 */
	public ResponseAdapter(Context context, List<Response> responseList) {
		this.dbm = new DatabaseManager(context);
		this.context = context;
		this.responseList = responseList;
	}

	@Override
	public int getCount() {
		if (responseList == null) {
			return 0;
		}
		return responseList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < 0 || position >= responseList.size()) {
			return null;
		}
		return responseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Update the number of votes received for a response (both in
	 * the database and in the view).
	 * @param rl - RelativeLayout containing the list item/response
	 * @param up - Indicates whether we are upvoting or downvoting, true
	 * if the former and false if the latter
	 */
	private void updateVotes(RelativeLayout rl, boolean up) {
		TextView ridView = (TextView) rl.findViewById(R.id.rid);
		TextView numVotes = (TextView) rl.findViewById(R.id.responseVotes);
		dbm.open();
		int n = 0;
		if (up == true) {
			n = dbm.incrementNumVotes(Integer.parseInt(ridView.getText().toString()));
		}
		else {
			n = dbm.decrementNumVotes(Integer.parseInt(ridView.getText().toString()));
		}
		dbm.close();
		numVotes.setText("" + n);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.template_response_listitem, parent, false);

		//Get all of the views in the list item.
		final RelativeLayout rl = (RelativeLayout) convertView;
		Response response = responseList.get(position);
		TextView responseText = (TextView) convertView.findViewById(R.id.responseText);
		TextView responseTime = (TextView) convertView.findViewById(R.id.responseTimestamp);
		TextView numVotes = (TextView) convertView.findViewById(R.id.responseVotes);
		TextView responseId = (TextView) convertView.findViewById(R.id.rid);
		Button upVote = (Button) convertView.findViewById(R.id.upVoteButton);
		Button downVote = (Button) convertView.findViewById(R.id.downVoteButton);
		
		//Update the views with the Response object's info, and register button listeners.
		//This will allow us to show a particular response and process upvote/downvote
		//requests.
		responseText.setText(response.getResponseText());
		responseTime.setText(response.getTimestamp());
		numVotes.setText("" + response.getVotes());
		responseId.setText("" + response.getResponseId());
		upVote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateVotes(rl, true);
			}			
		});
		
		downVote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateVotes(rl, false);
			}			
		});

		return convertView;
	}
}