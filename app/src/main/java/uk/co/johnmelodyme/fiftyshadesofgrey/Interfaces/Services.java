package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

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
}
