package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.InputStream;

import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class BookActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public Utilities utilities;
    public WebView webView;

    public String getBookAsset(Activity activity)
    {
        /* Get Utilities class */
        utilities = new Utilities(TAG);

        return utilities.getParsedData(activity, "book_asset");
    }

    public void renderComponents(Activity activity)
    {
        /* Set Content View */
        activity.setContentView(R.layout.activity_book);

        /* Declaration of layout user components */
        webView = (WebView) findViewById(R.id.book_view);

        /* Get Book Asset Location */
        this.getBookAsset(activity);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.renderComponents(BookActivity.this);

        /* Load Book asset */
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.loadUrl("file:///android_res/raw/" + this.getBookAsset(BookActivity.this));

        Log.d(TAG, "onCreate: "  + this.getBookAsset(BookActivity.this));
    }
}