package be.ucll.mobile.aphasia.Model;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import be.ucll.mobile.aphasia.R;

/**
 * Created by croewens on 04/01/2017.
 */

public class ResultAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();


    public ResultAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageRestult = (ImageView) row.findViewById(R.id.imageResult);
            holder.playButton = (Button) row.findViewById(R.id.buttonPlayResult) ;
            holder.counter = (TextView) row.findViewById(R.id.resultCount);
            holder.duration = (TextView) row.findViewById(R.id.duration);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        Result result = (Result) data.get(position);
        holder.imageRestult.setImageURI(Uri.parse(result.getImagePath()));

        holder.duration.setText(result.getDuration());
        //result.getImageItem().getPath()
        holder.playButton.setTag(result.getAnswer());
        holder.counter.setText("" + (position + 1));
        return row;
    }

    static class ViewHolder {
        TextView counter;
        ImageView imageRestult;
        Button playButton;
        TextView duration;
    }
}
