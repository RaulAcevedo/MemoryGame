package bajiomedia.com.accedotest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bajiomedia.com.accedotest.R;
import bajiomedia.com.accedotest.model.ScoreDetail;

/**
 * Created by Raúl on 11/10/2015.
 */
public class ScoreAdapter extends ArrayAdapter<ScoreDetail> {

    public ScoreAdapter(Context context, List<ScoreDetail> scoreList){
        super(context,0,scoreList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.score_item,parent,false);
        TextView text = (TextView)view.findViewById(R.id.nameText);
        text.setText(getItem(position).getName());
        text = (TextView)view.findViewById(R.id.scoreText);
        text.setText(getItem(position).getScore()+"");
        return view;
    }


}
