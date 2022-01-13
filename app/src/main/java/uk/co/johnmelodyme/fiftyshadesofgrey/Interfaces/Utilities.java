package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;

public final class Utilities
{
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final String default_notification_channel_id = "default";
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

    public void scheduleNotification(Context context, int delay)
    {
        Calendar cal = Calendar.getInstance();

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                100,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        cal.add(Calendar.SECOND, 15);

        assert alarmManager != null;

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

}
