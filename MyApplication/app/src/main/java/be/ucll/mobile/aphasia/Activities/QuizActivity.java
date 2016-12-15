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
import be.ucll.mobile.aphasia.Model.ImageItem;
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

    public void onButtonClick(View v){
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
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
        switch(request_code){
            case 100:   if(result_code==RESULT_OK && i !=null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.d("jow",result.get(0));
                resultTEXT = (TextView) findViewById(R.id.text);
                resultTEXT.setText(result.get(0));
            }
                break;

        }
    }



}
