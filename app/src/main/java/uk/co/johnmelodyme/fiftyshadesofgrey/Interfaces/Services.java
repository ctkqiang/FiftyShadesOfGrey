package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.MalformedURLException;
import java.net.URL;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class Services extends Service
{

    public String TAG;
    public Activity activity;

    public Services(String TAG, Activity activity)
    {
        this.TAG = TAG;
        this.activity = activity;
    }

    public void openBuyMeCoffee(String TAG)
    {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(this.activity.getString(R.string.buymecoffee))
        );

        this.activity.startActivity(browserIntent);

        Log.d(TAG, "=> onOpenBuyMeCoffee: #opening buy me a coffee web-page ");
    }

    public void downloadSource(String endpointUrl)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference httpsReference = storage.getReferenceFromUrl(endpointUrl);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void pushNotification(String payload)
    {
        NotificationManagerCompat notificationManagerCompat;
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    "channel",
                    this.activity.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = (NotificationManager) this.activity.getSystemService(
                    NotificationManager.class
            );

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this.activity.getApplicationContext(),
                "channel"
        );

        builder.setContentTitle(this.activity.getString(R.string.app_name));
        builder.setContentText(payload);
        builder.setSmallIcon(R.drawable.fifty);

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(
                this.activity.getApplicationContext()
        );

        notificationManagerCompat.notify(1, notification);

        Log.d(TAG, "pushNotification: " + payload);

    }

    @Override
    public void onCreate()
    {

        /* TODO Adjust this */
        this.pushNotification("some 50 shades quotes");

        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
