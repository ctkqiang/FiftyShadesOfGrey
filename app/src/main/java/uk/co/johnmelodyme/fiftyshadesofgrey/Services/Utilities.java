package uk.co.johnmelodyme.fiftyshadesofgrey.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Utilities
{
    public String TAG;

    public Utilities(String TAG)
    {
        this.TAG = TAG;
    }

    public void parseData(Context context, Class<?> classname, String key, String data)
    {
        Intent intent = new Intent(context, classname);
        intent.putExtra(key, data);
        context.startActivity(intent);

        Log.d(TAG, "parseData to another activity");
    }

    public String getParsedData(Activity activity, String key)
    {
        Intent intent = activity.getIntent();
        
        Log.d(TAG, "getParsedData from another activity");

        return intent.getStringExtra(key);
    }
}
