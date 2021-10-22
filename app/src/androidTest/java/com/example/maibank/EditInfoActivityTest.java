package com.example.maibank;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maibank.Activities.EditInfoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EditInfoActivityTest {

    @Rule
    public ActivityScenarioRule<EditInfoActivity> activityRule =
            new ActivityScenarioRule<>(EditInfoActivity.class);

    @Test
    public void testUpdateButton() {
        onView(withId(R.id.update_button)).check(matches(isDisplayed()));
    }

}
