package mobdao.com.openquiz.data.repositories.opentriviarepository

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode
import mobdao.com.openquiz.data.utils.extensions.toSingle
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.models.Question
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class OpenTriviaRepositoryUnitTests {

    @MockK
    private lateinit var retrofit: Retrofit
    @MockK
    private lateinit var questionServiceMapper: QuestionServiceMapper
    @MockK
    private lateinit var service: QuestionsService
    @MockK
    private lateinit var call: Call<QuestionsResponse>
    @MockK
    private lateinit var single: Single<QuestionsResponse>
    @MockK
    private lateinit var singleMap: Single<List<Question>>
    @MockK
    private lateinit var singleMappedResponse: Single<List<Question>>
    @MockK
    private lateinit var questionResponse: QuestionsResponse

    private val nOfQuestions = 10
    private val questions: List<Question> = emptyList()
    private lateinit var openTriviaRepository: OpenTriviaRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic("mobdao.com.openquiz.data.utils.extensions.CallKt")
        openTriviaRepository = OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
    }

    @Test
    fun `Passed correct number of questions to questions service`() {
        setupServiceCall()

        openTriviaRepository.fetchQuestions(nOfQuestions)

        verify(exactly = 1) { service.fetchQuestions(nOfQuestions) }
    }

    @Test(expected = RuntimeException::class)
    fun `Propagate exception if returned response code other than success`() {
        setupRequestWithFailureResponseCode()

        openTriviaRepository.fetchQuestions(nOfQuestions)
    }

    @Test
    fun `Response is passed to mapper if returned success response code`() {
        setupRequestWithSuccessResponseCode()

        openTriviaRepository.fetchQuestions(nOfQuestions)

        verify(exactly = 1) { questionServiceMapper.questionResponseToModel(questionResponse) }
    }

    @Test
    fun `Response mapper is passed to a Single just if returned success response code`() {
        setupRequestWithSuccessResponseCode()

        openTriviaRepository.fetchQuestions(nOfQuestions)

        verify(exactly = 1) { Single.just(questions) }
    }

    // region private

    private fun setupServiceCall() {
        every { retrofit.create(QuestionsService::class.java) }.returns(service)
        every { service.fetchQuestions(nOfQuestions) }.returns(call)
    }

    private fun fetchQuestionBasicSetup() {
        setupServiceCall()
        every { call.toSingle() }.returns(single)
        every { single.flatMap<List<Question>>(any()) }.answers {
            firstArg<(QuestionsResponse) -> Single<List<Question>>>().invoke(questionResponse)
            singleMap
        }
    }

    private fun setupRequestWithFailureResponseCode() {
        fetchQuestionBasicSetup()
        every { questionResponse.response_code }.returns(QuestionsResponseCode.NO_RESULTS.code)
    }

    private fun setupRequestWithSuccessResponseCode() {
        fetchQuestionBasicSetup()
        every { questionResponse.response_code }.returns(QuestionsResponseCode.SUCCESS.code)
        mockkObject(Single)
        every { questionServiceMapper.questionResponseToModel(any()) } returns questions
        every { Single.just<List<Question>>(any()) } returns singleMappedResponse
    }

    // endregion
}

