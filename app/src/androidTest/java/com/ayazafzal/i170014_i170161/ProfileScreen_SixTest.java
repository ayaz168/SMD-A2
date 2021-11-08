package com.ayazafzal.i170014_i170161;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.provider.MediaStore;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileScreen_SixTest extends TestCase {
    @Rule
    public ActivityTestRule<ProfileScreen_Six> ProfileScreen_SixTestRule=new ActivityTestRule<>(ProfileScreen_Six.class);
    private String fNameTest="Ayaz";
    private String lNameTest="Afzal";
    private String phoneTest="03481512600";
    private String genderTest="Male";
    private String bioTest="Slug, Slug, Slug";
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }
    @Test
    public void testUserInputScenario(){
        Intent inX=new Intent();
        ProfileScreen_SixTestRule.launchActivity(inX);
        ProfileScreen_SixTestRule.getActivity();
        onView(withId(R.id.newFirstName)).perform(typeText(fNameTest));
        onView(withId(R.id.newLastName)).perform(typeText(lNameTest));
        onView(withId(R.id.newAccountPhone)).perform(typeText(phoneTest));
        onView(withId(R.id.newAccountGender)).perform(typeText(genderTest));
        onView(withId(R.id.newAccountBio)).perform(typeText(bioTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.newFirstName)).check(matches(withText(fNameTest)));
        Espresso.onView(withId(R.id.newLastName)).check(matches(withText(lNameTest)));
        Espresso.onView(withId(R.id.newAccountPhone)).check(matches(withText(phoneTest)));
        Espresso.onView(withId(R.id.newAccountGender)).check(matches(withText(genderTest)));
        Espresso.onView(withId(R.id.newAccountBio)).check(matches(withText(bioTest)));

        Espresso.onView(withId(R.id.takeImageCamera)).perform(click());
        //intended(hasComponent(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult));
    }

    public void tearDown() throws Exception {
        Intents.release();
    }
}