package com.example.maibank;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maibank.activities.SendMoneyActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ConfirmTransferDialogTest {

        @Rule
        public ActivityScenarioRule<SendMoneyActivity> activityRule =
                new ActivityScenarioRule<>(SendMoneyActivity.class);

        @Test
        public void testDialogAppear() {
            onView(withId(R.id.btn_pay)).perform(click());
            onView(withText("Transfer money")).check(matches(isDisplayed()));
        }

}
