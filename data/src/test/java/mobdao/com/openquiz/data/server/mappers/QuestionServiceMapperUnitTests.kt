package mobdao.com.openquiz.data.server.mappers

import junit.framework.Assert.assertEquals
import mobdao.com.openquiz.data.server.responses.QuestionResponse
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.QuestionType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class QuestionServiceMapperUnitTests {

    private lateinit var questionServiceMapper: QuestionServiceMapper
    private val questionResponse = QuestionResponse(
        Category.ANIMALS,
        QuestionType.MULTIPLE_CHOICE,
        Difficulty.EASY,
        "question",
        "correct answer",
        listOf("incorrect 1", "incorrect 2")
    )
    private val questionsResponse = QuestionsResponse(null, listOf(questionResponse))

    @Before
    fun setup() {
        questionServiceMapper = QuestionServiceMapperImpl()
    }

    @Test
    fun `Return correct list of Questions when mapped response`() {
        val result = questionServiceMapper.questionResponseToModel(questionsResponse)

        with(result.first()) {
            assertEquals(questionResponse.category, category)
            assertEquals(questionResponse.type, type)
            assertEquals(questionResponse.difficulty, difficulty)
            assertEquals(questionResponse.question, question)
            assertEquals(questionResponse.correct_answer, correctAnswer)
            assertEquals(questionResponse.incorrect_answers, incorrectAnswers)
        }
    }
}