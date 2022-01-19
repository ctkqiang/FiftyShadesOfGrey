package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.NetworkInspector;
import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Services;
import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;


public class VideoActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public Utilities utilities;
    public Services services;
    public String endpoint;
    public ProgressBar progressBar;
    public NetworkInspector networkInspector;
    public SurfaceView surfaceview;

    public String getVideoUrl(Activity activity)
    {
        /* Get Utilities Class */
        this.utilities = new Utilities(TAG);
        this.services = new Services(TAG, VideoActivity.this);
        this.endpoint = utilities.getParsedData(activity, "movie_asset");

        if (endpoint == null)
            return "URL_EMPTY";

        return endpoint;
    }

    public void renderComponent(Activity activity)
    {
        /* Set content view to activity */
        activity.setContentView(R.layout.activity_video);

        /* Get NetworkInspector Class */
        networkInspector = new NetworkInspector();

        /* Render User Components */
        this.progressBar = this.findViewById(R.id.exo_progress);

        /* Render Player */
        this.surfaceview = this.findViewById(R.id.surfaceview);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        this.getVideoUrl(this);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            /* Remove Action Bar*/
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            /* Set Layout Fullscreen on playing video */
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );

            /* Set bottom navigation bar to black colour */
            this.getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        this.renderComponent(VideoActivity.this);

        /* Connectivity Inspection */
        if (! this.networkInspector.checkConnection(this))
        {
            this.services.pushNotification("No Internet Connection");
        }
    }

}
