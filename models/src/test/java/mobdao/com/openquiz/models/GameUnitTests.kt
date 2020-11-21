package mobdao.com.openquiz.models

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameUnitTests {

    private lateinit var game: Game
    private val correctAnswer = "correct answer"
    private val incorrectAnswer = "incorrect answer"
    @SpyK
    private var question = Question(
        Category.ANIMALS,
        QuestionType.MULTIPLE_CHOICE,
        Difficulty.EASY,
        "question",
        correctAnswer,
        listOf(incorrectAnswer)
    )
    private var question2 = Question(
        Category.ART,
        QuestionType.MULTIPLE_CHOICE,
        Difficulty.EASY,
        "question",
        correctAnswer,
        listOf(incorrectAnswer)
    )
    private val questions = listOf(question, question2)

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        game = Game(questions)
    }

    @Test
    fun `When getting next question should return it`() {
        val nextQuestion = game.nextQuestion()

        Assert.assertSame(question2, nextQuestion)
    }

    @Test
    fun `When getting next question without more questions should return null`() {
        game.nextQuestion()
        game.nextQuestion()
        val nextQuestion = game.nextQuestion()

        assertNull(nextQuestion)
    }

    @Test
    fun `When answering should delegate to question`() {
        game.answer(question, correctAnswer)

        verify { question.answer(correctAnswer) }
    }

    @Test
    fun `When answering correctly should increment current score`() {
        every { question.answer(any()) }.returns(true)

        val previousScore = game.score
        game.answer(question, correctAnswer)

        assertTrue(previousScore + 1 == game.score)
    }

    @Test
    fun `When answering incorrectly should not increment current score`() {
        every { question.answer(any()) }.returns(false)

        val previousScore = game.score
        game.answer(question, "OPTION")

        assertTrue(previousScore == game.score)
    }
}
