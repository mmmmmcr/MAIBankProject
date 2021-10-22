package com.example.maibank;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maibank.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class HomeWidgetTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testWidget() {
        onView(withId(R.id.input_min)).perform(replaceText(String.valueOf("1")),
                closeSoftKeyboard());
        onView(withId(R.id.input_max)).perform(replaceText(String.valueOf("10")),
                closeSoftKeyboard());
        onView(withId(R.id.btn_set_min_max)).perform(click());
        assertEquals(" < 0 %", getText(onView(withId(R.id.account_percentage))));
    }

    public String getText(ViewInteraction matcher){
        final String[] text = new String[1];
        ViewAction va = new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription(){
                return "Text of the view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                text[0] = tv.getText().toString();
            }
        };

        matcher.perform(va);

        return text[0];
    }

}
