package com.example.maibank;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.example.maibank.Activities.MainActivity;
import com.example.maibank.Activities.PayABillActivity;
import com.example.maibank.Activities.SendMoneyActivity;
import com.example.maibank.Activities.TransactionHistoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LaunchingActivitiesTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void navigatePayBill() {

        Activity instance = getActivityInstance();
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withText(instance.getResources().getString(R.string.btn_payBill))).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof PayABillActivity);
        assertTrue(b);
    }

    @Test
    public void navigateSendMoney() {

        Activity instance = getActivityInstance();
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withText(instance.getResources().getString(R.string.btn_sendMoney))).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof SendMoneyActivity);
        assertTrue(b);
    }

    @Test
    public void navigateTransactionHistory() {

        Activity instance = getActivityInstance();
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withText(instance.getResources().getString(R.string.transactionhistory))).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof TransactionHistoryActivity);
        assertTrue(b);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }
}