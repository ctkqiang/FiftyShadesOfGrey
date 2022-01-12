package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

public final class Utilities
{
    public static Utilities INSTANCE;
    public static String TAG;

    public static Utilities getInstance()
    {
        if (INSTANCE == null)
        {
            return INSTANCE = new Utilities(TAG);
        }

        return INSTANCE;
    }

    public Utilities(String TAG)
    {
        Utilities.TAG = TAG;
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
