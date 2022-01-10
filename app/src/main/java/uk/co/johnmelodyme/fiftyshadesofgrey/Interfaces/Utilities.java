package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

public class Utilities
{
    public String TAG;

    public Utilities(String TAG)
    {
        this.TAG = TAG;
    }

    public void parseData(Context context, Class<?> classname, String key, @NonNull String data)
    {
        Intent intent = new Intent(context, classname);
        intent.putExtra(key, data);
        context.startActivity(intent);

        Log.d(TAG, "parseData to another activity");
    }

    public String getParsedData(Activity activity, @NonNull String key)
    {
        Intent intent = activity.getIntent();

        Log.d(TAG, "getParsedData from another activity");

        return intent.getStringExtra(key);
    }
}
