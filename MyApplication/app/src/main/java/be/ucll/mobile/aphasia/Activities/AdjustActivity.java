package be.ucll.mobile.aphasia.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import be.ucll.mobile.aphasia.Model.AphasiaApplication;
import be.ucll.mobile.aphasia.Model.GridViewAdapter;
import be.ucll.mobile.aphasia.Model.ImageLoader;
import be.ucll.mobile.aphasia.R;

public class AdjustActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Button deleteButton;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private static String path = AphasiaApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolibrary);
        setToolbarTitle();
        deleteButton = (Button) findViewById(R.id.buttonDelete);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, ImageLoader.getImages());
        gridView.setAdapter(gridAdapter);
        gridView.setFadingEdgeLength(150);

    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Adjust");
    }

    public void deletePhoto(View view) {
        Button delete = (Button) view;
        Log.d("delete", delete.getTag().toString());

        File file = new File(delete.getTag().toString());
        file.delete();

        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, ImageLoader.getImages());
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
                Uri uri = data.getData();
                String filename = getFileName(uri);
                System.out.println(filename);

                try {
                    FileInputStream in = (FileInputStream) getContentResolver().openInputStream( data.getData());
                    OutputStream out = new FileOutputStream(new File(path + "/" + filename));
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = in.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, ImageLoader.getImages());
                gridView.setAdapter(gridAdapter);
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
