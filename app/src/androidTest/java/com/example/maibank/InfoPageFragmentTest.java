package com.example.maibank;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.example.maibank.activities.ContactActivity;
import com.example.maibank.activities.EditInfoActivity;
import com.example.maibank.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InfoPageFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void navigateCreateNewAccount() {
        int count = 2;
        while (count != 0) {
            onView(withId(R.id.pager)).perform(swipeLeft());
            count--;
        }
        onView(withId(R.id.create_new_account_btn)).perform(click());
        Activity activity = getActivityInstance();
        assertTrue((activity instanceof ContactActivity));
    }

    @Test
    public void navigateEditInfoButton() {
        int count = 3;
        while (count != 0) {
            onView(withId(R.id.pager)).perform(swipeLeft());
            count--;
        }
        onView(withId(R.id.edit_button)).perform(click());
        Activity activity = getActivityInstance();
        assertTrue(activity instanceof EditInfoActivity);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }
}