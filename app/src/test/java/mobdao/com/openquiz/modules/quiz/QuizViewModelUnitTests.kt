package mobdao.com.openquiz.modules.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.utils.pokos.ResultsReport
import org.junit.Assert.*
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
    private lateinit var showCorrectAnswerObserver: Observer<Unit>
    @MockK
    private lateinit var showNextQuestionObserver: Observer<Unit>
    @MockK
    private lateinit var showResultsReportObserver: Observer<ResultsReport>

    private val questions: List<Question> by lazy { listOf(question1, question2) }

    private lateinit var quizViewModel: QuizViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        quizViewModel = QuizViewModel()
        quizViewModel.init(game, questions)

        quizViewModel.questionsLiveData.observeForever(questionsObserver)
        quizViewModel.getShowCorrectAnswerEvent(question1)?.observeForever(showCorrectAnswerObserver)
        quizViewModel.showNextQuestionEvent.observeForever(showNextQuestionObserver)
        quizViewModel.showResultsReportEvent.observeForever(showResultsReportObserver)
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
    fun `Delegate answer to game when clicked to confirm answer`() {
        quizViewModel.onConfirmAnswerClicked(question1, "answer")

        verify { game.answer(any(), any()) }
    }

    @Test
    fun `Trigger to show correct answer when clicked to confirm answer`() {
        quizViewModel.onConfirmAnswerClicked(question1, "answer")

        verify { showCorrectAnswerObserver.onChanged(null) }
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

        quizViewModel.onNextClicked()

        val resultsReport = quizViewModel.showResultsReportEvent.value
        assertEquals(1, resultsReport?.correctAnswers)
        assertEquals(0, resultsReport?.wrongAnswers)
        verify { showResultsReportObserver.onChanged(any()) }
    }

    @Test
    fun `Return null when passed a invalid question to showCorrectAnswerEvents()`() {
        val questionMock: Question = mockk()
        val result = quizViewModel.getShowCorrectAnswerEvent(questionMock)

        assertNull(result)
    }
}