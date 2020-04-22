package mobdao.com.openquiz.modules.quiz.question

import android.graphics.Color
import android.view.View
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.R
import mobdao.com.openquiz.di.factories.ViewModelFactory
import mobdao.com.openquiz.models.*
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class QuestionFragmentTest {

    @MockK
    lateinit var viewModelFactoryMock: ViewModelFactory

    lateinit var viewModel: QuizViewModel

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
    private val questions = listOf(question)
    private val game = Game(listOf(question))

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = QuizViewModel()
        every { viewModelFactoryMock.create(QuizViewModel::class.java) }.returns(viewModel)
        viewModel.init(game, questions)

        launchFragmentInContainer(
            fragmentArgs = bundleOf(QUESTION to question),
            instantiate = {
                val fragment = TestQuestionFragment()
                fragment.fakeViewModelFactory = viewModelFactoryMock
                fragment
            }
        )
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

    class TestQuestionFragment : QuestionFragment() {

        lateinit var fakeViewModelFactory: ViewModelFactory

        override var viewModelFactory: ViewModelProvider.Factory
            get() = fakeViewModelFactory
            set(value) {}
    }

    fun withTextColor(expectedColor: Int): Matcher<View?>? {
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

}