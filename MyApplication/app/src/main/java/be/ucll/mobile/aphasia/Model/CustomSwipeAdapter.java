package be.ucll.mobile.aphasia.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import be.ucll.mobile.aphasia.R;

/**
 * Created by Sebboz on 14-Dec-16.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private ArrayList<ImageItem> imagelist = getAllImagesInDevice();

    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }



    @Override
    public int getCount() {
       return imagelist.size();
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
            imageView.setImageBitmap(imagelist.get(position).getImage());
            int realpos = position + 1;
            textView.setText("Exercise "+realpos);

            container.addView(item_view);

        return item_view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public ArrayList<ImageItem> getAllImagesInDevice(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures/images";
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath(),bmOptions);
            imageItems.add(new ImageItem(bitmap,files[i].getName(),files[i].getAbsolutePath()));
            Log.d("Files", "FileName:" + files[i].getName());
        }
        return imageItems;
    }

}
