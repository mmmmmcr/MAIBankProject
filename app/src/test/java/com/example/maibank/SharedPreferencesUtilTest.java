package com.example.maibank;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.maibank.Util.SharedPreferencesUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtilTest {
    private static final String TEST = "test";
    private final Map<String, String> pref = new HashMap<>();
    private Activity activity;

    @Before
    public void setUp(){
        pref.clear();
        activity = mockPreferences(pref);
    }

    @Test
    public void testSharedPreferencesPut() {
        SharedPreferencesUtil.saveToPreferences(TEST, TEST, activity);
        Assert.assertEquals("test", pref.get(TEST));
    }

    @Test
    public void testSharedPreferencesGet() {
        SharedPreferencesUtil.saveToPreferences(TEST, TEST, activity);
        Assert.assertEquals(TEST, SharedPreferencesUtil.get(TEST, activity));
    }

    private Activity mockPreferences(final Map<String, String> pref) {
        Activity activity = Mockito.mock(Activity.class);
        SharedPreferences preferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation){
                return pref.get(invocation.getArguments()[0]);
            }
        });
        final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenAnswer(new Answer<SharedPreferences.Editor>() {
            @Override
            public SharedPreferences.Editor answer(InvocationOnMock invocation){
                pref.put((String) invocation.getArguments()[0], (String) invocation.getArguments()[1]);
                return editor;
            }
        });
        Mockito.when(editor.commit()).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation){
                return true;
            }
        });
        Mockito.when(preferences.edit()).thenReturn(editor);
        Mockito.when(activity.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(preferences);
        return activity;
    }
}
