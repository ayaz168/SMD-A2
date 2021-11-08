package com.ayazafzal.i170014_i170161;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LogIn_ThreeTest extends TestCase {
    @Rule
    public ActivityTestRule<LogIn_Three> LogIn_ThreeTestRule=new ActivityTestRule<>(LogIn_Three.class);
    private String emailTest="ayaz1234@gmail.com";
    private String passTest="1234";
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }
    @Test
    public void testUserInputScenario(){
        Intent inX=new Intent();
        LogIn_ThreeTestRule.launchActivity(inX);
        LogIn_ThreeTestRule.getActivity();
        onView(withId(R.id.newEmailScreenThree)).perform(typeText(emailTest));
        onView(withId(R.id.newPasswordScreenThree)).perform(typeText(passTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.logInScreenThree)).perform(click());
        intended(hasComponent(LogIn_Three.class.getName()));

    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}