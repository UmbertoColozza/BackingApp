package com.umberto.backingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.umberto.backingapp.ui.MainActivity;
import com.umberto.backingapp.ui.RecipeListStepDescriptionActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final CharSequence RECIPE_NAME = "Nutella";

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem() {

        // RecyclerView item 0 clicks it.
        onView(withId(R.id.rvRecipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        // Checks that the RecipeListStepDescriptionActivity opens
        intended(hasComponent(RecipeListStepDescriptionActivity.class.getName()));
    }
}
