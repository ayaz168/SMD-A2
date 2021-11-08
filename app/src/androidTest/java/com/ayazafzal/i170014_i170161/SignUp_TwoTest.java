package com.ayazafzal.i170014_i170161;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class SignUp_TwoTest extends TestCase {
    @Rule
    //ActivityTestRule<SignUp_Two> rule= new ActivityTestRule(SignUp_Two.class);
    public ActivityTestRule<SignUp_Two> SignUp_TwoTestRule=new ActivityTestRule<>(SignUp_Two.class);
    private String emailTest="ayaz1234@gmail.com";
    private String passTest="1234";
    private  String cpassTest="1234";
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }
    @Test
    public void testUserInputScenario(){
        //Intent inX=new Intent();
        //SignUp_TwoTestRule.launchActivity(inX);
        //SignUp_TwoTestRule.getActivity();
        onView(withId(R.id.newEmailScreenTwo)).perform(typeText(emailTest));
        onView(withId(R.id.newPasswordScreenTwo)).perform(typeText(passTest));
        onView(withId(R.id.newcPasswordScreenTwo)).perform(typeText(cpassTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.signUpScreenTwo)).perform(click());
        intended(hasComponent(ProfileScreen_Six.class.getName()));
        /*Espresso.onView(withId(R.id.newEmailScreenTwo)).check(matches(withText(emailTest)));
        Espresso.onView(withId(R.id.newPasswordScreenTwo)).check(matches(withText(passTest)));
        Espresso.onView(withId(R.id.newcPasswordScreenTwo)).check(matches(withText(cpassTest)));
        /*onView(withId(R.id.newEmailScreenTwo).withText("Hello!"))
                .perform(typeText("Hello"),click())
                .check(matches(withText("Hello!")));*/
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}