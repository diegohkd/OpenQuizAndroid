package mobdao.com.openquiz.modules.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode
import mobdao.com.openquiz.data.utils.extensions.toSingle
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit

class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @MockK
    private lateinit var startQuizObserver: Observer<List<Question>>

    @MockK
    private lateinit var disposable: Disposable

    private val nOfQuestions = 10
    private val questions = listOf(
        Question(
            Category.ANIMALS,
            QuestionType.MULTIPLE_CHOICE,
            Difficulty.EASY,
            "question1",
            "correctAnswer",
            listOf("incorrectAnswer1", "incorrectAnswer2")
        ),
        Question(
            Category.ANIMALS,
            QuestionType.MULTIPLE_CHOICE,
            Difficulty.EASY,
            "question2",
            "correctAnswer",
            listOf("incorrectAnswer1", "incorrectAnswer2")
        )
    )

    private lateinit var userAuthRepository: UserAuthRepository
    private lateinit var openTriviaRepository: OpenTriviaRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic("mobdao.com.openquiz.data.utils.extensions.CallKt")
        mockkObject(Single)
        userAuthRepository = UserAuthRepositoryImpl(mockk(), mockk())
        openTriviaRepository = OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
        homeViewModel = HomeViewModel(userAuthRepository, openTriviaRepository)
        homeViewModel.startQuizEvent.observeForever(startQuizObserver)
    }

    @Test
    fun onClickToStartQuizShouldTriggerEventToStartItWithCorrectQuestions() {
        setupRequestWithSuccessResponseCode()

        homeViewModel.onStartQuizClicked()

        verify { startQuizObserver.onChanged(questions) }
    }

    // region private

    private fun setupRequestWithSuccessResponseCode() {
        every { retrofit.create(QuestionsService::class.java) }.returns(service)
        every { service.fetchQuestions(nOfQuestions) }.returns(call)
        every { call.toSingle() }.returns(single)
        every { single.flatMap<List<Question>>(any()) }.answers {
            firstArg<(QuestionsResponse) -> Single<List<Question>>>().invoke(questionResponse)
            singleMap
        }
        every { singleMap.subscribeBy(any()) }.answers {
            firstArg<Callback<List<Question>>>().success?.invoke(questions)
            disposable
        }
        every { questionResponse.response_code }.returns(QuestionsResponseCode.SUCCESS.code)
        every { questionServiceMapper.questionResponseToModel(any()) } returns questions
        every { Single.just<List<Question>>(any()) } returns singleMappedResponse
    }

    // endregion
}