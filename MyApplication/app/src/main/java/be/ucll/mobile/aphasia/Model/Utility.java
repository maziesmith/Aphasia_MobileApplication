package be.ucll.mobile.aphasia.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by croewens on 04/01/2017.
 */

public class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView, int total, int start, int rest) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int item = 0;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
            item = listItem.getMeasuredHeight();
        }

        int offset = (listAdapter.getCount() - 1);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + offset + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        while (total - (start + rest) < height) {
            height -= item;
        }

        params.height = height + item / 3;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void zip(File[] files, String zipFile) throws IOException {
        try  {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[2048];
            for(int i=0; i < files.length; i++) {
                if (files[i].getPath().substring(files[i].getPath().lastIndexOf(".")).equals(".zip")) {
                    break;
                }
                FileInputStream fi = new FileInputStream(files[i].getPath());
                origin = new BufferedInputStream(fi, 2048);

                ZipEntry entry = new ZipEntry(files[i].getPath().substring(files[i].getPath().lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
