package be.ucll.mobile.aphasia.Model;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by tompl on 12/23/2016.
 */

public class ImageLoader {

    private static String path = AphasiaApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/images";

    public static ArrayList<ImageItem> getImages(){

        File directory = new File(path);

        if (directory.listFiles() == null || directory.listFiles().length == 0) {
            File file = new File(path);
            file.mkdirs();
            transferImagesFromAssetsToExternal();
        }

        return loadFromExternal(directory);
    }


    private static void transferImagesFromAssetsToExternal(){

        AssetManager assets = AphasiaApplication.getAssetsManager();
        try {

            String[] pictureNames = assets.list("pictures");

            for (int i = 0; i < pictureNames.length; i++) {
                String picturePath = "pictures/"+pictureNames[i];
                InputStream in = assets.open(picturePath);
                OutputStream out = new FileOutputStream(new File(path + "/" + pictureNames[i]));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            }

        } catch (IOException e) {
            Log.d("from assets to external", e.getMessage());
        }


    }

    private static ArrayList<ImageItem>  loadFromExternal(File directory){
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath(), bmOptions);
            imageItems.add(new ImageItem(bitmap, files[i].getName(), files[i].getAbsolutePath()));
        }

        return imageItems;
    }

}
