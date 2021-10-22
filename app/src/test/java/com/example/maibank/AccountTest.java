package com.example.maibank;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maibank.fragments.HomePageFragment;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AccountTest extends TestCase {
    @Test
    public void testAccountUpperLimit() {
        final String[] set = new String[1];
        EditText min = Mockito.mock(EditText.class);
        EditText max = Mockito.mock(EditText.class);
        Editable editableMin = Mockito.mock(Editable.class);
        Mockito.when(editableMin.toString()).thenReturn("10");
        Editable editableMax = Mockito.mock(Editable.class);
        Mockito.when(editableMax.toString()).thenReturn("100");
        Mockito.when(min.getText()).thenReturn(editableMin);
        Mockito.when(max.getText()).thenReturn(editableMax);
        HomePageFragment mock = new HomePageFragment();
        TextView textView = Mockito.mock(TextView.class);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                set[0] = (String) invocation.getArguments()[0];
                return null;
            }
        }).when(textView).setText(Mockito.anyString());
        mock.updateAccountLimits(min, max,1000, textView);
        assertEquals(" > 100 %", set[0]);
    }

    @Test
    public void testAccountLowerLimit() {
        final String[] set = new String[1];
        EditText min = Mockito.mock(EditText.class);
        EditText max = Mockito.mock(EditText.class);
        Editable editableMin = Mockito.mock(Editable.class);
        Mockito.when(editableMin.toString()).thenReturn("10");
        Editable editableMax = Mockito.mock(Editable.class);
        Mockito.when(editableMax.toString()).thenReturn("100");
        Mockito.when(min.getText()).thenReturn(editableMin);
        Mockito.when(max.getText()).thenReturn(editableMax);
        HomePageFragment mock = new HomePageFragment();
        TextView textView = Mockito.mock(TextView.class);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                set[0] = (String) invocation.getArguments()[0];
                return null;
            }
        }).when(textView).setText(Mockito.anyString());
        mock.updateAccountLimits(min, max,1, textView);
        assertEquals(" < 0 %", set[0]);
    }

    @Test
    public void testAccountInLimits() {
        final String[] set = new String[1];
        EditText min = Mockito.mock(EditText.class);
        EditText max = Mockito.mock(EditText.class);
        Editable editableMin = Mockito.mock(Editable.class);
        Mockito.when(editableMin.toString()).thenReturn("10");
        Editable editableMax = Mockito.mock(Editable.class);
        Mockito.when(editableMax.toString()).thenReturn("100");
        Mockito.when(min.getText()).thenReturn(editableMin);
        Mockito.when(max.getText()).thenReturn(editableMax);
        HomePageFragment mock = new HomePageFragment();
        TextView textView = Mockito.mock(TextView.class);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                set[0] = (String) invocation.getArguments()[0];
                return null;
            }
        }).when(textView).setText(Mockito.anyString());
        mock.updateAccountLimits(min, max,45, textView);
        assertEquals("38.89 %", set[0]);
    }
}
