/*
no-icon
Icons made by Pixel perfect
https://www.flaticon.com/authors/pixel-perfect
from  www.flaticon.com
is licensed by Creative Commons BY 3.0 - CC 3.0 BY
http://creativecommons.org/licenses/by/3.0/
 */

package com.umberto.backingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
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
    private ImageView ivRecipeStep;
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
        ivRecipeStep = (ImageView)rootView.findViewById(R.id.ivRecipeStep);
        initExoPlayer();
        if(rootView.findViewById(R.id.tvStepInstruction)!=null) {
            // Get a reference to the TextView instruction in the fragment layout
            final TextView tvStepInstruction = (TextView) rootView.findViewById(R.id.tvStepInstruction);
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
                    setMediaPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
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
                    setMediaPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
                }
            });

            //Hide buttons if is tablet
            if(mTwoPane){
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
            }
        }

        // Return the rootView
        return rootView;
    }

    /**
     * Initialize ExoPlayer.
     * @param thumbnailUri The URI string of the sample to play.
     * @param videoUri The URI string of the sample to play.
     */
    private void setMediaPlayer(String thumbnailUri, String videoUri) {
        if(mExoPlayer!=null){
            mExoPlayer.stop();
        }

        //Check if correct uri. If is empty show no_camera image.
        Uri mediaUri;
        if(!TextUtils.isEmpty(thumbnailUri)){
            mediaUri=Uri.parse(thumbnailUri);

            //Set loading animation and center
            ivRecipeStep.setScaleType(ImageView.ScaleType.CENTER);
            ivRecipeStep.setImageResource(R.drawable.progress_animation);

            Picasso.with(this.getContext()).load(mediaUri)
                    .noPlaceholder()
                    .error(R.drawable.no_camera)
                    .into(ivRecipeStep,new Callback() {
                        @Override
                        public void onSuccess() {
                            //if image load success set scaltetype to center crop
                            ivRecipeStep.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }

                        @Override
                        public void onError() {
                            ivRecipeStep.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                    });
            ivRecipeStep.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        } else if(!TextUtils.isEmpty(videoUri)){
            mediaUri=Uri.parse(videoUri);
            ivRecipeStep.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this.getContext(), "BackingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
            mExoPlayer.seekTo(playerPosition);
        } else {
            ivRecipeStep.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ivRecipeStep.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            ivRecipeStep.setImageResource(R.drawable.no_camera);
            return;
        }
    }

    private void initExoPlayer(){
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }
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

        initExoPlayer();
        setMediaPlayer(recipe.getSteps().get(currentStep).getThumbnailURL(), recipe.getSteps().get(currentStep).getVideoURL());
    }
    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
