package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;
import uk.co.johnmelodyme.fiftyshadesofgrey.Services.Utilities;

public class SelectorActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public Utilities utilities;

    public int getPageId(Activity activity)
    {
        /* Get Utilities class */
        utilities = new Utilities(TAG);

        return Integer.parseInt(utilities.getParsedData(activity, "id"));
    }

    public void renderComponents()
    {
        /* Set content view to activity */
        SelectorActivity.this.getWindow().setContentView(R.layout.activity_selector);

        /* if else logic */

        Toast.makeText(SelectorActivity.this, String.valueOf(this.getPageId(SelectorActivity.this)),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.renderComponents();
    }
}