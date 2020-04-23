package mobdao.com.openquiz.data.server.mappers

import org.junit.Assert.assertEquals
import mobdao.com.openquiz.data.server.responses.QuestionResponse
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.QuestionType
import org.junit.Before
import org.junit.Test

class QuestionServiceMapperUnitTests {

    private lateinit var questionServiceMapper: QuestionServiceMapper
    private val validQuestionResponse = QuestionResponse(
        Category.ANIMALS,
        QuestionType.MULTIPLE_CHOICE,
        Difficulty.EASY,
        "question",
        "correct answer",
        listOf("incorrect 1", "incorrect 2")
    )
    private val questionsResponse = QuestionsResponse(null, listOf(validQuestionResponse))

    @Before
    fun setup() {
        questionServiceMapper = QuestionServiceMapperImpl()
    }

    @Test
    fun `Return correct list of Questions when mapped response`() {
        val result = questionServiceMapper.questionResponseToModel(questionsResponse)

        with(result.first()) {
            assertEquals(validQuestionResponse.category, category)
            assertEquals(validQuestionResponse.type, type)
            assertEquals(validQuestionResponse.difficulty, difficulty)
            assertEquals(validQuestionResponse.question, question)
            assertEquals(validQuestionResponse.correct_answer, correctAnswer)
            assertEquals(validQuestionResponse.incorrect_answers, incorrectAnswers)
        }
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's category is null`() {
        val invalidQuestionResponse = QuestionResponse(
            null,
            validQuestionResponse.type,
            validQuestionResponse.difficulty,
            validQuestionResponse.question,
            validQuestionResponse.correct_answer,
            validQuestionResponse.incorrect_answers
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's type is null`() {
        val invalidQuestionResponse = QuestionResponse(
            validQuestionResponse.category,
            null,
            validQuestionResponse.difficulty,
            validQuestionResponse.question,
            validQuestionResponse.correct_answer,
            validQuestionResponse.incorrect_answers
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's difficulty is null`() {
        val invalidQuestionResponse = QuestionResponse(
            validQuestionResponse.category,
            validQuestionResponse.type,
            null,
            validQuestionResponse.question,
            validQuestionResponse.correct_answer,
            validQuestionResponse.incorrect_answers
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's question is null`() {
        val invalidQuestionResponse = QuestionResponse(
            validQuestionResponse.category,
            validQuestionResponse.type,
            validQuestionResponse.difficulty,
            null,
            validQuestionResponse.correct_answer,
            validQuestionResponse.incorrect_answers
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's correct_answer is null`() {
        val invalidQuestionResponse = QuestionResponse(
            validQuestionResponse.category,
            validQuestionResponse.type,
            validQuestionResponse.difficulty,
            validQuestionResponse.question,
            null,
            validQuestionResponse.incorrect_answers
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }

    @Test(expected = RuntimeException::class)
    fun `Throw exception if response's incorrect_answers is null`() {
        val invalidQuestionResponse = QuestionResponse(
            validQuestionResponse.category,
            validQuestionResponse.type,
            validQuestionResponse.difficulty,
            validQuestionResponse.question,
            validQuestionResponse.correct_answer,
            null
        )
        val invalidQuestionsResponse = QuestionsResponse(null, listOf(invalidQuestionResponse))
        questionServiceMapper.questionResponseToModel(invalidQuestionsResponse)
    }
}