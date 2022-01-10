package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class BookActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public Utilities utilities;
    public WebView webView;
    public ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

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
        this.webView.requestFocus();
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl("https://docs.google.com/viewer?embedded=true&url=https://raw"
                             + ".githubusercontent.com/johnmelodyme/FiftyShadesOfGrey"
                             + "/cc13e798b23f607b19af1d2087da0d1d78c85c08/app/src/main/assets/" +
                             this.getBookAsset(BookActivity.this)

        );

        this.webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                view.loadUrl("https://docs.google.com/viewer?embedded=true&url=https://raw"
                             + ".githubusercontent.com/johnmelodyme/FiftyShadesOfGrey"
                             + "/cc13e798b23f607b19af1d2087da0d1d78c85c08/app/src/main/assets/" +
                             getBookAsset(BookActivity.this)
                );
                return true;
            }
        });

        this.webView.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView view, int progress)
            {
                if (progress < 100)
                {
                    progressDialog.show();
                }
                if (progress == 100)
                {
                    progressDialog.dismiss();
                }
            }
        });

        Log.d(TAG, "onCreate: " + this.getBookAsset(BookActivity.this));
    }
}