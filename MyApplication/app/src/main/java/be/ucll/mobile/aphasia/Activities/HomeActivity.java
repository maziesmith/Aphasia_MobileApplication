package be.ucll.mobile.aphasia.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.ucll.mobile.aphasia.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setFonts();
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Home");
    }

    private void setFonts() {

        TextView txt = (TextView) findViewById(R.id.slogan);
        Button start = (Button) findViewById(R.id.start_test);
        Button results = (Button) findViewById(R.id.results);
        Button adjust = (Button) findViewById(R.id.adjust);

        Typeface latoLightItalic = Typeface.createFromAsset(getAssets(), "fonts/Lato-LightItalic.ttf");
        Typeface latoBold = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");

        txt.setTypeface(latoLightItalic);
        start.setTypeface(latoBold);
        results.setTypeface(latoBold);
        adjust.setTypeface(latoBold);
    }

    public void openResultsIntent(View view) {
        Intent i = new Intent(this, ResultsActivity.class);
        i.setAction(Intent.ACTION_VIEW );
        startActivity(i);
    }

    public void openAdjustIntent(View view) {
        Intent i = new Intent(this, AdjustActivity.class);
        i.setAction(Intent.ACTION_VIEW );
        startActivity(i);
    }

    public void openQuizIntent(View view) {
        Intent i = new Intent(this, QuizActivity.class);
       i.setAction(Intent.ACTION_VIEW );
        startActivity(i);
    }
}
