package com.umberto.backingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.umberto.backingapp.data.Recipe;
import com.umberto.backingapp.data.Step;
import com.umberto.backingapp.ui.MainActivity;
import com.umberto.backingapp.ui.RecipeListStepDescriptionActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class RecipeListStepDescriptionActivityTest {

    //Using intent extra data with my rule
    @Rule
    public ActivityTestRule<RecipeListStepDescriptionActivity> mActivityRule =
            new ActivityTestRule<RecipeListStepDescriptionActivity>(RecipeListStepDescriptionActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeListStepDescriptionActivity.class);
                    result.putExtra("recipe", getFakeRecipe());
                    return result;
                }
            };

    @Test
    public void clickRecyclerViewItem() {
        //Check if recyclerview of step list is displayed
        onView(withId(R.id.rvRecipeStepDescription)).check(matches(isDisplayed()));
    }

    private Recipe getFakeRecipe(){
        Recipe recipe=new Recipe();
        ArrayList<Step> steps=new ArrayList<Step>();
        Step step=new Step();
        step.setShortDescription("Step 1");
        steps.add(step);
        recipe.setSteps(steps);
        return recipe;
    }
}
