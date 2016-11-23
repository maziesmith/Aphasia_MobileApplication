package be.ucll.mobile.aphasia.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import be.ucll.mobile.aphasia.R;

/**
 * Created by tompl on 11/22/2016.
 */

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myresults);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Results");
    }

}
