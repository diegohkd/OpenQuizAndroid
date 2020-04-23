package mobdao.com.openquiz.data.repositories.opentriviarepository

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode
import mobdao.com.openquiz.data.utils.exceptions.QuestionsException
import mobdao.com.openquiz.models.Question
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
class OpenTriviaRepositoryUnitTests {

    @MockK
    private lateinit var retrofit: Retrofit

    @MockK
    private lateinit var questionServiceMapper: QuestionServiceMapper

    @MockK
    private lateinit var service: QuestionsService

    @MockK
    private lateinit var questionResponse: QuestionsResponse

    private val nOfQuestions = 10
    private val questions: List<Question> = emptyList()
    private lateinit var openTriviaRepository: OpenTriviaRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        openTriviaRepository = OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
    }

    @Test
    fun `Passed correct number of questions to questions service`() = runBlockingTest {
        setupRequestWithSuccessResponseCode()

        openTriviaRepository.fetchQuestions(nOfQuestions)

        coVerify(exactly = 1) { service.fetchQuestions(nOfQuestions) }
    }

    @Test(expected = QuestionsException::class)
    fun `Throws QuestionsException if returned response code other than success`() =
        runBlockingTest {
            setupRequestWithFailureResponseCode()

            openTriviaRepository.fetchQuestions(nOfQuestions)
        }

    @Test
    fun `Response is passed to mapper if returned success response code`() = runBlockingTest {
        setupRequestWithSuccessResponseCode()

        openTriviaRepository.fetchQuestions(nOfQuestions)

        verify(exactly = 1) { questionServiceMapper.questionResponseToModel(questionResponse) }
    }

    // region private

    private fun setupServiceCall() = runBlockingTest {
        every { retrofit.create(QuestionsService::class.java) }.returns(service)
        coEvery { service.fetchQuestions(nOfQuestions) }.returns(questionResponse)
    }

    private fun setupRequestWithFailureResponseCode() {
        setupServiceCall()
        every { questionResponse.response_code }.returns(QuestionsResponseCode.NO_RESULTS.code)
    }

    private fun setupRequestWithSuccessResponseCode() {
        setupServiceCall()
        every { questionResponse.response_code }.returns(QuestionsResponseCode.SUCCESS.code)
        every { questionServiceMapper.questionResponseToModel(any()) } returns questions
    }

    // endregion
}

