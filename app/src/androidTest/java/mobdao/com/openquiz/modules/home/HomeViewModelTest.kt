package mobdao.com.openquiz.modules.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import io.mockk.*
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.R
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    private lateinit var questionResponse: QuestionsResponse

    @MockK
    private lateinit var routeObserver: Observer<NavDirections>

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
        userAuthRepository = UserAuthRepositoryImpl(mockk(), mockk())
        openTriviaRepository = OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
        homeViewModel = HomeViewModel(userAuthRepository, openTriviaRepository)
        homeViewModel.routeEvent.observeForever(routeObserver)
    }

    @Test
    fun onClickToStartQuizShouldTriggerEventToStartItWithCorrectQuestions() {
        setupRequestWithSuccessResponseCode()

        homeViewModel.onStartQuizClicked()

        val slot = slot<NavDirections>()
        verify { routeObserver.onChanged(capture(slot)) }
        assertEquals(R.id.to_quizFragment, slot.captured.actionId)
        assertEquals(questions, slot.captured.arguments.getParcelableArray("questions")!!.toList())
    }

    // region private

    private fun setupRequestWithSuccessResponseCode() {
        every { retrofit.create(QuestionsService::class.java) }.returns(service)
        coEvery { service.fetchQuestions(nOfQuestions) }.returns(questionResponse)
        every { questionResponse.response_code }.returns(QuestionsResponseCode.SUCCESS.code)
        every { questionServiceMapper.questionResponseToModel(any()) } returns questions
    }

    // endregion
}