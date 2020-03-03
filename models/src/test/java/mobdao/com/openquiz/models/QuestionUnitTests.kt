package mobdao.com.openquiz.models

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class QuestionUnitTests {

    private lateinit var question: Question
    private val correctAnswer = "correct answer"
    private val incorrectAnswer = "incorrect answer"

    @Before
    fun setup() {
        question = Question(
            Category.ANIMALS,
            QuestionType.MULTIPLE_CHOICE,
            Difficulty.EASY,
            "question",
            correctAnswer,
            listOf(incorrectAnswer)
        )
    }

    @Test
    fun `When creating question should not have answeredOption`() {
        assertNull(question.answeredOption)
    }

    @Test
    fun `When creating question should have answeredOption`() {
        question.answer(correctAnswer)

        assertEquals(correctAnswer, question.answeredOption)
    }

    @Test
    fun `when answering with correct option should return true`() {
        val result = question.answer(correctAnswer)

        assertTrue(result)
    }

    @Test
    fun `when answering with incorrect option should return false`() {
        val result = question.answer(incorrectAnswer)

        assertFalse(result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when answering with invalid option should throw exception`() {
        question.answer("INVALID")
    }
}