package be.ucll.mobile.aphasia.Activities;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import be.ucll.mobile.aphasia.Model.CustomSwipeAdapter;

import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class QuizActivity extends AppCompatActivity{
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    TextView resultTEXT;
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setToolbarTitle();
        //slide view
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Quiz");
    }

    public void onButtonClick(View v){
        Intent intent = new Intent(this, AudioActivity.class);

        int pos = viewPager.getCurrentItem()+1;
        Log.d("test","Exercise"+pos);
        intent.putExtra("oefening", "Exercise"+pos);
        startActivityForResult(intent,100);
    }

    public void onNextClick(View v) {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem+1);
        if(currentItem+1 >= adapter.getCount()){
            Toast.makeText(QuizActivity.this,"You've reached the end of the test, enjoy the rest of your day!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
        }
    }
    public void onPreviousClick(View v) {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem-1);
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
        TextView text = (TextView) viewPager.findViewById(R.id.text2);
        text.setText("Answer recorded , go to the next one or record again");
    }



}
