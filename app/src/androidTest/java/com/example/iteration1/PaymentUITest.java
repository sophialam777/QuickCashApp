package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Test;

public class PaymentUITest {


    @Test
    public void testUIElementsDisplayed() {
        ActivityScenario.launch(PaymenActivity.class);

        onView(withId(R.id.textView2)).check(matches(withText("Payment")));

        onView(withId(R.id.textView4)).check(matches(withText("Enter the Amount you want to pay:")));

        onView(withId(R.id.editTextText)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.button)).check(matches(withText("Pay")));
    }

    @Test
    public void testNoAmountEntered() {
        ActivityScenario.launch(PaymenActivity.class);

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.editTextText)).check(matches(hasErrorText("Amount cannot be empty")));
    }


}
