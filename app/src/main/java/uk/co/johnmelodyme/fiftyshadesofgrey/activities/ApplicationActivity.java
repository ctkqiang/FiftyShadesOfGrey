package uk.co.johnmelodyme.fiftyshadesofgrey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.widget.ListView;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class ApplicationActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public ListView listView;

    public void renderComponents()
    {
        /* Set content view to activity */
        ApplicationActivity.this.getWindow().setContentView(R.layout.activity_main);

        /* declaration of layout user components */
        listView = (ListView) findViewById(R.id.list_menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Render Components */
        this.renderComponents();
    }
}