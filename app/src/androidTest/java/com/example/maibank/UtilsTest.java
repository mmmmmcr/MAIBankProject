package com.example.maibank;

import android.content.Context;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maibank.util.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UtilsTest {
    @Test
    public void testValidEmail() {
        boolean result;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EditText text = new EditText(appContext);
        text.setText("alexandr.barcan@gmail.com");
        result = Utils.isEmailValid(text);
        assertTrue(result);
    }

    @Test
    public void testInvalidEmail() {
        boolean result;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EditText text = new EditText(appContext);
        text.setText("bobo");
        result = Utils.isEmailValid(text);
        assertFalse(result);
    }

    @Test
    public void testInvalidPassword() {
        boolean result;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EditText text = new EditText(appContext);
        text.setText("apa");
        result = Utils.isPasswordFieldOk(text);
        assertFalse(result);
    }

    @Test
    public void testValidPassword() {
        boolean result;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EditText text = new EditText(appContext);
        text.setText("parolabuna");
        result = Utils.isPasswordFieldOk(text);
        assertTrue(result);
    }

}