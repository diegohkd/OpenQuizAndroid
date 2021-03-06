package mobdao.com.openquiz.modules.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import mobdao.com.openquiz.R
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.utils.pokos.ResultsReport
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuizViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var game: Game

    @MockK
    private lateinit var question1: Question

    @MockK
    private lateinit var question2: Question

    @MockK
    private lateinit var questionsObserver: Observer<List<Question>>

    @MockK
    private lateinit var confirmAnswerObserver: Observer<Unit>

    @MockK
    private lateinit var showCorrectAnswerObserver: Observer<Boolean>

    @MockK
    private lateinit var showNextQuestionObserver: Observer<Unit>

    @MockK
    private lateinit var routeObserver: Observer<NavDirections>

    private val questions: List<Question> by lazy { listOf(question1, question2) }

    private lateinit var quizViewModel: QuizViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        quizViewModel = QuizViewModel()
        quizViewModel.init(game, questions)

        quizViewModel.questionsLiveData.observeForever(questionsObserver)
        quizViewModel.getConfirmAnswerEvent(question1)?.observeForever(confirmAnswerObserver)
        quizViewModel.getShowCorrectAnswerEvent(question1)
            ?.observeForever(showCorrectAnswerObserver)
        quizViewModel.showNextQuestionEvent.observeForever(showNextQuestionObserver)
        quizViewModel.routeEvent.observeForever(routeObserver)
    }

    @Test
    fun `Initialize game on init`() {
        assertNotNull(game)
    }

    @Test
    fun `Initialize correct answers events for every question on init`() {
        assertNotNull(quizViewModel.getShowCorrectAnswerEvent(question1))
        assertNotNull(quizViewModel.getShowCorrectAnswerEvent(question2))
    }

    @Test
    fun `Show questions on init`() {
        verify { questionsObserver.onChanged(questions) }
    }

    @Test
    fun `Trigger to confirm answer when clicked to confirm answer`() {
        quizViewModel.onConfirmAnswerClicked(question1)

        verify { confirmAnswerObserver.onChanged(null) }
    }

    @Test
    fun `Delegate answer to game when confirming answer`() {
        quizViewModel.onConfirmAnswer(question1, "answer")

        verify { game.answer(any(), any()) }
    }

    @Test
    fun `Trigger to show correct answer when confirming answer`() {
        quizViewModel.onConfirmAnswer(question1, "answer")

        verify { showCorrectAnswerObserver.onChanged(true) }
    }

    @Test
    fun `Trigger to show next question when clicked next`() {
        every { game.nextQuestion() }.returns(question2)

        quizViewModel.onNextClicked()

        verify { showNextQuestionObserver.onChanged(null) }
    }

    @Test
    fun `Trigger to show final result when clicked next and has no more questions`() {
        every { game.nextQuestion() }.returns(null)
        every { game.getNumberOfCorrectAnswers() }.returns(1)
        every { game.getNumberOfIncorrectAnswers() }.returns(0)
        mockkObject(QuizFragmentDirections.Companion)

        quizViewModel.onNextClicked()

        val slotResultsReport = slot<ResultsReport>()
        verify { QuizFragmentDirections.Companion.toResultsReportFragment(capture(slotResultsReport)) }
        val resultsReport = slotResultsReport.captured
        val slotNavDirection = slot<NavDirections>()
        verify { routeObserver.onChanged(capture(slotNavDirection)) }
        assertEquals(1, resultsReport.correctAnswers)
        assertEquals(0, resultsReport.wrongAnswers)
        assertEquals(R.id.to_resultsReportFragment, slotNavDirection.captured.actionId)
    }

    @Test
    fun `Return null when passed a invalid question to showCorrectAnswerEvents()`() {
        val questionMock: Question = mockk()
        val result = quizViewModel.getShowCorrectAnswerEvent(questionMock)

        assertNull(result)
    }
}
