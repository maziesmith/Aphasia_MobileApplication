package be.ucll.mobile.aphasia.Activities;


import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import be.ucll.mobile.aphasia.Model.CustomSwipeAdapter;
import be.ucll.mobile.aphasia.Model.ImageItem;
import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class QuizActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setToolbarTitle();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

    }


    private ArrayList<ImageItem> getData() throws IOException {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        Resources res = getResources(); //if you are in an activity
        AssetManager am = res.getAssets();
        String[] imgPath = am.list("images");
        for (int j = 0; j< imgPath.length; j++) {
            InputStream is = am.open("images/" + imgPath[j]);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            imageItems.add(new ImageItem(bitmap, imgPath[j]));
        }
        return imageItems;
    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Quiz");
    }
}
