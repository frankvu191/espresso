package com.mytaxi.android_demo.activities;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void mainActivityTest() {

        // Login
        ViewInteraction usernameField = onView(allOf(withId(R.id.edt_username), isDisplayed()));
        usernameField.perform(replaceText(DataFactory.USERNAME), closeSoftKeyboard());

        ViewInteraction passwordField = onView(allOf(withId(R.id.edt_password), isDisplayed()));
        passwordField.perform(replaceText(DataFactory.PASSWORD), closeSoftKeyboard());

        ViewInteraction loginButton = onView(allOf(withId(R.id.btn_login), isDisplayed()));
        loginButton.perform(click());

        /* Wait for search text to display and type in
           This is not good practice and should be improved by implicit wait
         */
        SystemClock.sleep(1500);

        ViewInteraction searchField = onView(allOf(withId(R.id.textSearch), isDisplayed()));
        searchField.perform(typeText(DataFactory.SEARCH_KEYWORD), closeSoftKeyboard());

        // Wait for search result to display and tap on Sara Scott
        SystemClock.sleep(1500);

        onView(withText(DataFactory.DRIVER_NAME)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).perform(click());


        // Wait for driver profile to display and verify driver name is Sarah Scott
        SystemClock.sleep(1500);

        ViewInteraction textView = onView(allOf(withId(R.id.textViewDriverName), withText(DataFactory.DRIVER_NAME), isDisplayed()));
        textView.check(matches(withText(DataFactory.DRIVER_NAME)));

        // Go back and logout
        pressBack();

        ViewInteraction leftTopMenuButton = onView(allOf(withContentDescription("Open navigation drawer"), isDisplayed()));
        leftTopMenuButton.perform(click());

        ViewInteraction logoutButton = onView(allOf(withText("Logout"), isDisplayed()));
        logoutButton.perform(click());
    }
}
