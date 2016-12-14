package be.ucll.mobile.aphasia.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class AdjustActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolibrary);
        setToolbarTitle();
        getAllPictures();
        gridView = (GridView) findViewById(R.id.gridView);
        try {
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(gridAdapter);
       /* GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(AdjustActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Adjust");
    }
    public void getAllPictures() {
        Resources res = getResources(); //if you are in an activity
        AssetManager am = res.getAssets();
        String fileList[] = new String[0];
        try {
            fileList = am.list("images");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                Log.d("hallo", fileList[i]);
            }
        }
    }

        /*String path ="C:\Users\Michiel\AndroidStudioProjects\Aphasia\aphasia\MyApplication\app\src\main\assets";
        Log.d(path, "getAllPictures: ");
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
       File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
       {
           Log.d("Files", "FileName:" + files[i].getName());
        }*/
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
    public void deletePhoto(View view) {
        Button delete = (Button) findViewById(R.id.buttonDelete);

    }



}
