package com.example.auth

import android.Manifest
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

internal class LoginActivityTest

@RunWith(AndroidJUnit4ClassRunner::class)
class AuthInstrumentedTest {

    @get:Rule
    var grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.INTERNET)

    @Test
    fun testSoftkeyHide() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        assertTrue(!isKeyboardShown())
    }

    @Test
    fun testSoftkeyShown() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.user_name_text_field)).perform(ViewActions.click())
        assertTrue(isKeyboardShown())
    }
    @Test
    fun testFocusToSecondField() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.user_name_text_field)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.user_name_text_field))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field)).check(matches(hasFocus()));

    }

    @Test
    fun testFocusButton() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        Espresso.onView(ViewMatchers.withId(R.id.enter_button)).check(matches(hasFocus()));
    }

    @Test
    fun LogintestVisability() {

        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.image_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.auth_token_text_field))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.enter_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.user_name_text_field))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}


fun isKeyboardShown(): Boolean {
    val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
        Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText
}