package mobdao.com.openquiz.modules.quiz.question

import android.graphics.Color
import android.view.View
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import mobdao.com.openquiz.DataBindingIdlingResourceRule
import mobdao.com.openquiz.R
import mobdao.com.openquiz.launchFragmentInHiltContainer
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType
import mobdao.com.openquiz.modules.quiz.QuizFragment
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class QuestionFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private val correctAnswer = "correctAnswer"
    private val incorrectAnswer1 = "incorrectAnswer1"
    private val incorrectAnswer2 = "incorrectAnswer2"
    private val question = Question(
        Category.ANIMALS,
        QuestionType.MULTIPLE_CHOICE,
        Difficulty.EASY,
        "question1",
        correctAnswer,
        listOf(incorrectAnswer1, incorrectAnswer2)
    )
    private val questions = arrayOf(question)

    @Before
    fun setup() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxUnitFun = true)
        setupFragment()
    }

    @Test
    fun onSelectedAnswerShouldEnableConfirmButton() {
        onView(withText(correctAnswer)).perform(click())
        onView(withId(R.id.confirmButton)).check(matches(isEnabled()))
    }

    @Test
    fun onClickedConfirmShouldShowNextButton() {
        onView(withText(correctAnswer)).perform(click())
        onView(withId(R.id.confirmButton))
            .perform(click())
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.nextButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onSelectedWrongAnswerShouldHighlightCorrectAnswer() {
        onView(withText(incorrectAnswer1)).perform(click())
        onView(withId(R.id.confirmButton))
            .perform(click())
        onView(withText(correctAnswer))
            .check(matches(withTextColor(Color.GREEN)))
        onView(withText(incorrectAnswer1))
            .check(matches(withTextColor(Color.RED)))
    }

    @Test
    fun onSelectedCorrectAnswerShouldHighlightOnlyCorrectAnswer() {
        onView(withText(correctAnswer)).perform(click())
        onView(withId(R.id.confirmButton))
            .perform(click())
        onView(withText(correctAnswer))
            .check(matches(withTextColor(Color.GREEN)))
        onView(withText(incorrectAnswer1))
            .check(matches(not(withTextColor(Color.RED))))
        onView(withText(incorrectAnswer2))
            .check(matches(not(withTextColor(Color.RED))))
    }

    // region private

    private fun setupFragment() {
        // TODO I'm having to create a QuizFragment instead of a QuestionFragment, because the
        // QuizViewModel is called only by QuizFragment. Can this be improved?
        val scenario = launchFragmentInHiltContainer<QuizFragment>(
            fragmentArgs = bundleOf("questions" to questions)
        )
        dataBindingIdlingResourceRule.monitorActivity(scenario)
    }

    private fun withTextColor(expectedColor: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RadioButton>(RadioButton::class.java) {
            override fun matchesSafely(radioButton: RadioButton): Boolean {
                return radioButton.currentTextColor == expectedColor
            }

            override fun describeTo(description: Description) {
                description.appendText("with text color: ")
                description.appendValue(expectedColor)
            }
        }
    }

    // endregion private
}
