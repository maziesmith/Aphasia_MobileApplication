package be.ucll.mobile.aphasia.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import be.ucll.mobile.aphasia.Model.AphasiaApplication;
import be.ucll.mobile.aphasia.Model.ImageItem;
import be.ucll.mobile.aphasia.Model.ImageLoader;
import be.ucll.mobile.aphasia.Model.Result;
import be.ucll.mobile.aphasia.Model.ResultAdapter;
import be.ucll.mobile.aphasia.Model.Utility;
import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class ResultsActivity extends AppCompatActivity {

    static final String CONST_RESULTS = "results";

    private ListView resultsListView;
    private Button send;

    private ResultAdapter resultAdapter;

    private ArrayList<Result> results;

    private int totalHeight;

    MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myresults);
        setToolbarTitle();

        results = new ArrayList<>();

        send = (Button) findViewById(R.id.resultsSend);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendToDoctor();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        totalHeight = metrics.heightPixels;
    }

    @Override
    protected void onResume() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TypeToken<ArrayList<Result>> token = new TypeToken<ArrayList<Result>>() {};

        results = new Gson().fromJson(preferences.getString(CONST_RESULTS, null), token.getType());

        if (results == null) {
            results = new ArrayList<>();
        }
        showResults();
        super.onResume();
    }

    private void showResults() {
        //calculated stuff
        int total = totalHeight;
        int start = 285; //getRelativeTop(resultsListView); <- legit gemeten maar krijg niet working cuz layout niet geladen..
        int rest = 110;

        updateResults();

        Utility.setListViewHeightBasedOnChildren(resultsListView, total, start, rest);
    }

    private void updateResults() {
        resultAdapter = new ResultAdapter(ResultsActivity.this, R.layout.result,  results);
        resultsListView = (ListView) findViewById(R.id.results);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play(results.get(position).getPath());
            }
        });

        resultsListView.setAdapter(resultAdapter);
    }

    private void play(String path) {
        try {
            mp.reset();
            mp.setDataSource(path);
            mp.prepare();

            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateResults() {
        ArrayList<ImageItem> images = ImageLoader.getImages();
        results = new ArrayList<Result>();
        for (int i = 0; i < images.size(); i++) {
            results.add(new Result(images.get(i), "test", null, 0));
        }
    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Results");
    }

    private void sendToDoctor() {
       if (results == null || results.isEmpty()) {
            Toast.makeText(this,"No results yet!",Toast.LENGTH_LONG).show();
            return;
       }
       String output = "Doctor, here are the results for the Aphasia test: \n";
        ArrayList<String> paths = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>();

       for (int i = 0; i < results.size(); i++) {
           String path = results.get(i).getImagePath();
           File file = new File(path);
           String name = file.getName();
           if (!paths.contains(path)) {
               String list = name + ": \n";
               int counter = 1;
               for (int j = i; j < results.size(); j++) {
                   if(path.equals(results.get(j).getImagePath())) {
                       String audio = results.get(j).getPath();
                       file = new File(audio);

                       files.add(file);

                       String audioName = file.getName();

                       list += counter + ": " + audioName + ": \n";
                       counter++;
                   }
               }

               list += "\n\n";
               output += list;
               paths.add(path);
           }
       }

        ArrayList<File> images = new ArrayList<>();
        for (String s : paths) {
            images.add(new File(s));
        }

        String savePath = AphasiaApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath() + "/exercises.zip";

        List<File> both = new ArrayList<File>(files.size() + images.size());
        Collections.addAll(both, files.toArray(new File[files.size()]));
        Collections.addAll(both, images.toArray(new File[images.size()]));

        File[] filesToZip = both.toArray(new File[both.size()]);

        System.out.println( filesToZip.length);

        try {
            Utility.zip(filesToZip, savePath );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri path = Uri.fromFile(new File(savePath));
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        String to[] = {"d@ct.or"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Aphasia Results");
        emailIntent.putExtra(Intent.EXTRA_TEXT, output);
        startActivity(emailIntent);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.emptyResults);
        ListView list = (ListView) findViewById(R.id.results);
        list.setEmptyView(empty);
    }

    //voor het meten hoe groot de listviews moest zijn
    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
}
