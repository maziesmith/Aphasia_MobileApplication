package be.ucll.mobile.aphasia;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

}
