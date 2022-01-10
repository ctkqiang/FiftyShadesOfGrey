package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;


public class VideoActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public Utilities utilities;
    public SimpleExoPlayer exoPlayer;
    public SimpleExoPlayerView playerView;
    public BandwidthMeter bandwidthMeter;
    public TrackSelector trackSelector;


    public String getVideoUrl(Activity activity)
    {
        /* Get Utilities Class */
        utilities = new Utilities(TAG);

        return utilities.getParsedData(activity, "movie_asset");
    }

    public void renderComponent(Activity activity)
    {
        /* Set content view to activity */
        activity.setContentView(R.layout.activity_video);

        /* Render Player */
        this.playerView = (SimpleExoPlayerView) this.findViewById(R.id.player_view);

        try
        {
            this.bandwidthMeter = new DefaultBandwidthMeter();

            this.trackSelector = new DefaultTrackSelector(
                    new AdaptiveTrackSelection.Factory(bandwidthMeter)
            );

            this.exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoUri = Uri.parse(this.getVideoUrl(this));

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(
                    "exoplayer_video"
            );

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(
                    videoUri,
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null
            );

            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
        catch (Exception e)
        {
            Log.e(TAG, " exoplayer error " + e.toString());
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        this.getVideoUrl(this);
        Log.d(TAG, this.getVideoUrl(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.renderComponent(VideoActivity.this);
    }
}