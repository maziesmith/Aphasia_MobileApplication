package be.ucll.mobile.aphasia.Activities;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import be.ucll.mobile.aphasia.Model.CustomSwipeAdapter;

import be.ucll.mobile.aphasia.Model.ImageItem;
import be.ucll.mobile.aphasia.Model.ImageLoader;
import be.ucll.mobile.aphasia.Model.Result;
import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class QuizActivity extends AppCompatActivity{

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    TextView resultTEXT;
    public static String PACKAGE_NAME;

    private ArrayList<Result> results;
    static final String CONST_RESULTS = "results";

    //index voor files bij te houden. zou ook file skunnen tellen bvb
    static final String CONST_COUNTER = "quiz_counter";
    private int counter = -1;
    String filepath = Environment.getExternalStorageDirectory().getPath();
    File file = new File(filepath, "AudioRecorder");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setToolbarTitle();
        //slide view
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                TextView text = (TextView) findViewById(R.id.text2);
                if(text!=null){
                    text.setText("Record your answer, you can allways record it again if it fails");
                    text.requestLayout();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Quiz");
    }

    public void onButtonClick(View v){
        Intent intent = new Intent(this, AudioActivity.class);
        //int pos = viewPager.getCurrentItem()+1;
        //Log.d("test","Exercise"+pos);
        intent.putExtra("oefening", "Exercise"+counter);
        startActivityForResult(intent,100);
    }

    public void onNextClick(View v) {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem+1);
        TextView text = (TextView) findViewById(R.id.text2);
        if(text!=null){
            text.setText("Record your answer, you can allways record it again if it fails");
            text.requestLayout();
        }
        if(currentItem+1 >= adapter.getCount()){
            Toast.makeText(QuizActivity.this,"You've reached the end of the test, enjoy the rest of your day!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
        }
    }
    public void onPreviousClick(View v) {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem-1);

        TextView text = (TextView) findViewById(R.id.text2);
        if(text!=null){
            text.setText("Record your answer, you can allways record it again if it fails");
            text.requestLayout();
        }
    }

    public void promptSpeechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something!");
        try{
            startActivityForResult(i,100);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(QuizActivity.this,"Sorry! Your device doesn't support speach language!",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code , Intent i){
        super.onActivityResult(request_code,result_code,i);

        try{
            addResult();
            TextView text = (TextView) findViewById(R.id.text2);
            if(text!=null){
                text.setText("Answer recorded , go to the next one or record again");
                text.requestLayout();
            }
        }catch(RuntimeException ex){
            //Ignore
        }

    }



    private void addResult() {
        String path =  (file.getAbsolutePath() + "/" + ("Exercise"+counter).replaceAll(" ","") + ".mp4");
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();

        Result result = new Result(ImageLoader.getImages().get(viewPager.getCurrentItem()), "TEST ANSWER", path, Integer.parseInt(duration), new Date());
        results.add(result);
        counter += 1;
        System.out.println("add" + counter);
    }

    @Override
    protected void onResume() {
        //onresume meerdere keren gecalled zonder onpause?
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TypeToken<ArrayList<Result>> token = new TypeToken<ArrayList<Result>>() {
        };

        if (results == null || results.isEmpty()) {
            results = new Gson().fromJson(preferences.getString(CONST_RESULTS, null), token.getType());
            if (results == null) {
                results = new ArrayList<Result>();
            }
        }

        if (counter == -1) {
            counter = preferences.getInt(CONST_COUNTER, 1);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("ONPAUSE" + counter);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CONST_RESULTS, new Gson().toJson(results));
        editor.putInt(CONST_COUNTER, counter);

        editor.commit();

        super.onPause();
    }
}
