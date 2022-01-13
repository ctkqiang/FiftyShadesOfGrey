package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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
    public SimpleExoPlayer exoPlayer;
    public SimpleExoPlayerView playerView;
    public BandwidthMeter bandwidthMeter;
    public TrackSelector trackSelector;
    public ProgressBar progressBar;
    public ExtractorMediaSource mediaSource;
    public NetworkInspector networkInspector;

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
        this.progressBar = (ProgressBar) this.findViewById(R.id.exo_progress);

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

            this.playerView.setPlayer(exoPlayer);
            this.exoPlayer.prepare(mediaSource);
            this.exoPlayer.setPlayWhenReady(true);
            this.exoPlayer.setVolume(100);
            this.exoPlayer.addListener(new ExoPlayerEventListener());

            /* Set Subtitle */
            this.setCaptions();
        }
        catch (Exception e)
        {
            Log.e(TAG, " exoplayer error " + e.toString());
        }
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
        if (!this.networkInspector.checkConnection(this))
        {
            this.services.pushNotification("No Internet Connection");
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.exoPlayer.release();
        this.exoPlayer.stop();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.exoPlayer.release();
        this.exoPlayer.stop();
        this.exoPlayer = null;
    }

    public void setCaptions()
    {
        if (this.getSubtitle() == null)
        {
            Log.d(TAG, "No Captions found for this movie");
        }

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, "exo-demo")
        );

        MediaSource contentMediaSource = buildMediaSource(
                Uri.parse(this.getVideoUrl(VideoActivity.this))
        );

        MediaSource[] mediaSources = new MediaSource[2];
        mediaSources[0] = contentMediaSource;

        /* Add subtitles */
        SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(
                this.getSubtitle(),
                dataSourceFactory,
                null,
                C.TIME_UNSET
        );

        mediaSources[1] = subtitleSource;

        MediaSource mediaSource = new MergingMediaSource(mediaSources);

        this.exoPlayer.prepare(mediaSource);
        this.exoPlayer.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri parse)
    {
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, "50ShadesOfGrey"),
                new DefaultBandwidthMeter()
        );

        this.mediaSource = new ExtractorMediaSource(parse, dataSourceFactory,
                new DefaultExtractorsFactory(), new Handler(), null
        );

        return this.mediaSource;
    }

    public Uri getSubtitle()
    {
        int id = Integer.parseInt(this.utilities.getParsedData(this, "movie_id"));

        switch (id)
        {
            case 0:
                return Uri.parse("file:///android_asset/fifty_shades_of_grey_captions.srt");
            case 1:
            case 2:
            default:
                return null;
        }
    }

    private class ExoPlayerEventListener implements ExoPlayer.EventListener
    {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest)
        {
            /* Do nothing */
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups,
                                    TrackSelectionArray trackSelections)
        {
            /* Do nothing */
        }

        @Override
        public void onLoadingChanged(boolean isLoading)
        {
            /* Do nothing */
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
        {

            /* Keep Phone Screen STATE.ON while video is playing */
            this.onSetScreenStatus(playbackState);

            /* Render Buffering animation */
            this.onProgressBar(playbackState);

            if (playbackState == exoPlayer.STATE_READY)
            {
                services.pushNotification(getResources().getString(R.string.buffeing_msg));
            }

            if (playbackState == exoPlayer.STATE_BUFFERING)
            {
                services.pushNotification(getResources().getString(R.string.loading_movie));
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error)
        {
            /* Do nothing */
        }

        @Override
        public void onPositionDiscontinuity()
        {
            /* Do nothing */
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
        {
            /* Do nothing */
        }

        public void onSetScreenStatus(int playbackState)
        {
            if (playbackState == exoPlayer.STATE_IDLE)
            {
                playerView.setKeepScreenOn(false);
            }

            playerView.setKeepScreenOn(playbackState != exoPlayer.STATE_ENDED);
        }

        public void onProgressBar(int state)
        {
            if (state == exoPlayer.STATE_BUFFERING)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}