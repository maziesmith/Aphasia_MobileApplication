package be.ucll.mobile.aphasia.Model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.ucll.mobile.aphasia.R;

/**
 * Created by Sebboz on 14-Dec-16.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources = {R.drawable.ex11,R.drawable.ex4,R.drawable.ex3,R.drawable.ex2};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }



    @Override
    public int getCount() {
       return image_resources.length;
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
            imageView.setImageResource(image_resources[position]);
            int realpos = position + 1;
            textView.setText("Oefening "+realpos);
            container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
