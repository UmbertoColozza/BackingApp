package com.umberto.backingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.umberto.backingapp.R;
import com.umberto.backingapp.data.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeMediaFragment extends Fragment {
    // Final Strings to store state information about the recipe and current step
    public static final String RECIPE = "recipe";
    public static final String CURRENT_STEP = "current_step";
    public static final String TWO_PANE = "two_pane";
    public static final String PLAYER_POSITION = "player_position";
    public static final String PLAYER_STATE = "player_state";

    private Recipe recipe;
    private int currentStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private boolean mTwoPane;

    private long playerPosition;
    private boolean isPlayWhenReady;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeMediaFragment(){
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
            currentStep=savedInstanceState.getInt(CURRENT_STEP);
            mTwoPane=savedInstanceState.getBoolean(TWO_PANE);
            playerPosition=savedInstanceState.getLong(PLAYER_POSITION);
            isPlayWhenReady=savedInstanceState.getBoolean(PLAYER_STATE);
            Log.d("Recipe","savedInstanceState "+(recipe.getSteps()==null));
        }
        else
        {
            playerPosition=0;
            isPlayWhenReady=false;
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        if(rootView.findViewById(R.id.tvStepInstruction)!=null) {
            // Get a reference to the TextView instruction in the fragment layout
            final TextView tvStepInstruction = (TextView) rootView.findViewById(R.id.tvStepInstruction);
            Log.d("Recipe","currentStep "+currentStep+" --- "+(recipe==null));
            tvStepInstruction.setText(recipe.getSteps().get(currentStep).getDescription());
            // Get a reference to the Button previous in the fragment layout
            final Button btnPrevious = (Button) rootView.findViewById(R.id.button_previous);
            // Get a reference to the Button previous in the fragment layout
            final Button btnNext = (Button) rootView.findViewById(R.id.button_next);

            if (currentStep == 0) {
                btnPrevious.setVisibility(View.INVISIBLE);
            }
            if (currentStep == recipe.getSteps().size() - 1) {
                btnNext.setVisibility(View.INVISIBLE);
            }
            btnPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentStep--;
                    if (currentStep == 0) {
                        btnPrevious.setVisibility(View.INVISIBLE);
                    }
                    btnNext.setVisibility(View.VISIBLE);
                    tvStepInstruction.setText(recipe.getSteps().get(currentStep).getDescription());
                    setPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
                }
            });
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentStep++;
                    if (currentStep + 1 == recipe.getSteps().size()) {
                        btnNext.setVisibility(View.INVISIBLE);
                    }
                    btnPrevious.setVisibility(View.VISIBLE);
                    tvStepInstruction.setText(recipe.getSteps().get(currentStep).getDescription());
                    setPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
                }
            });

            //Hide buttons if is tablet
            if(mTwoPane){
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
            }
        }

        // Initialize the player.
        setPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
        // Return the rootView
        return rootView;
    }

    /**
     * Initialize ExoPlayer.
     * @param thumbnailUri The URI string of the sample to play.
     * @param videoUri The URI string of the sample to play.
     */
    private void setPlayer(String thumbnailUri, String videoUri) {
        //Check if correct uri. If is empty return.
        Uri mediaUri;
        if(!TextUtils.isEmpty(thumbnailUri)){
            mediaUri=Uri.parse(thumbnailUri);
        } else if(!TextUtils.isEmpty(videoUri)){
            mediaUri=Uri.parse(videoUri);
        } else {
            /*if(mExoPlayer!=null){
                mExoPlayer.stop();
            }*/
            mPlayerView.setVisibility(View.INVISIBLE);
            return;
        }
        mPlayerView.setVisibility(View.VISIBLE);

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }
        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(this.getContext(), "BackingApp");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(isPlayWhenReady);
        mExoPlayer.seekTo(playerPosition);
    }

    public void setRecipe(Recipe recipe, int initialStep, boolean mTwoPane){
        this.recipe=recipe;
        this.currentStep=initialStep;
        this.mTwoPane=mTwoPane;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE,recipe);
        outState.putInt(CURRENT_STEP,currentStep);
        outState.putBoolean(TWO_PANE,mTwoPane);

        if(mExoPlayer!=null)
        playerPosition = mExoPlayer.getCurrentPosition();
        outState.putLong(PLAYER_POSITION,playerPosition);
        boolean isPlayWhenReady = mExoPlayer.getPlayWhenReady();
        outState.putBoolean(PLAYER_STATE, isPlayWhenReady);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Release ExoPlayer.
        releasePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
