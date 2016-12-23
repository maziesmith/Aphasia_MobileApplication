package be.ucll.mobile.aphasia.Model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import be.ucll.mobile.aphasia.R;

/**
 * Created by Sebboz on 14-Dec-16.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private ArrayList<ImageItem> images;
    private LayoutInflater layoutInflater;
    private Context ctx;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
        images = ImageLoader.getImages();
    }

    @Override
    public int getCount() {
       return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
            ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
            TextView textView = (TextView) item_view.findViewById(R.id.image_count);
            imageView.setImageBitmap(images.get(position).getImage());
            int realpos = position + 1;
            textView.setText("Exercise "+realpos);

            container.addView(item_view);

        return item_view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
