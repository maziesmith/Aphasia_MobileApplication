package be.ucll.mobile.aphasia.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
    private Button deleteButton;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolibrary);
        setToolbarTitle();
        deleteButton = (Button) findViewById(R.id.buttonDelete);
       // getAllPictures();

        gridView = (GridView) findViewById(R.id.gridView);
        //try {
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getAllImagesInDevice());
     /*   } catch (IOException e) {
            e.printStackTrace();
        }*/
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
      /*  private ArrayList<ImageItem> getData() throws IOException {
            final ArrayList<ImageItem> imageItems = new ArrayList<>();
            Resources res = getResources(); //if you are in an activity
            AssetManager am = res.getAssets();
            String[] imgPath = am.list("images");
            for (int j = 0; j< imgPath.length; j++) {
                InputStream is = am.open("images/" + imgPath[j]);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageItems.add(new ImageItem(bitmap, imgPath[j],"jhbnhb"));
            }
            return imageItems;
        }*/
    public void deletePhoto(View view) {
        Button delete = (Button) view;
        Log.d("delete", delete.getTag().toString());
        File file = new File(delete.getTag().toString());
        boolean deleted = file.delete();
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getAllImagesInDevice());
        gridView.setAdapter(gridAdapter);


    }
    public void addPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }



    public ArrayList<ImageItem> getAllImagesInDevice(){
            final ArrayList<ImageItem> imageItems = new ArrayList<>();
          //  File sd = Environment.getExternalStorageDirectory();
           // File image = new File(sd+filePath, imageName);
           // BitmapFactory.Options bmOptions = new BitmapFactory.Options();
           // Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
           // bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            //imageItems.add(new ImageItem(bitmap,"jhjh"));
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
