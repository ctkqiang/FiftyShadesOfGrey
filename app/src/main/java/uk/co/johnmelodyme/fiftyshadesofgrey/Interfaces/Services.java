package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.MalformedURLException;
import java.net.URL;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class Services
{
    public static void openBuyMeCoffee(String TAG, Context context)
    {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(context.getString(R.string.buymecoffee))
        );

        context.startActivity(browserIntent);

        Log.d(TAG, "=> onOpenBuyMeCoffee: #opening buy me a coffee web-page ");
    }

    public static void downloadSource(String endpointUrl)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference httpsReference = storage.getReferenceFromUrl(endpointUrl);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void pushNotification(String payload, Activity activity)
    {
        NotificationManagerCompat notificationManagerCompat;
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    "channel",
                    activity.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = (NotificationManager) activity.getSystemService(
                    NotificationManager.class
            );

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                activity.getApplicationContext(),
                "channel"
        );

        builder.setContentTitle(activity.getString(R.string.app_name));
        builder.setContentText(payload);
        builder.setSmallIcon(R.drawable.fifty);

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(
                activity.getApplicationContext()
        );

        notificationManagerCompat.notify(1, notification);

    }
}
