package uk.co.johnmelodyme.fiftyshadesofgrey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class ApplicationActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}