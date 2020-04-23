package mobdao.com.openquiz.modules.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.models.Question
import mobdao.com.CoroutineTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var userAuthRepository: UserAuthRepository

    @MockK
    private lateinit var openTriviaRepository: OpenTriviaRepository

    @MockK
    private lateinit var showProgressBarObserver: Observer<Boolean>

    @MockK
    private lateinit var genericErrorObserver: Observer<Unit>

    @MockK
    private lateinit var startQuizObserver: Observer<List<Question>>

    @MockK
    private lateinit var signOutObserver: Observer<Unit>

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(userAuthRepository, openTriviaRepository)
        homeViewModel.showProgressBarEvent.observeForever(showProgressBarObserver)
        homeViewModel.genericErrorEvent.observeForever(genericErrorObserver)
        homeViewModel.startQuizEvent.observeForever(startQuizObserver)
        homeViewModel.signOutEvent.observeForever(signOutObserver)
    }

    @Test
    fun `Show progress bar when clicked to start quiz`() {
        setupFetchQuestions()

        homeViewModel.onStartQuizClicked()

        verify { showProgressBarObserver.onChanged(true) }
    }

    @Test
    fun `Hide progress bar if failed to fetch questions`() {
        setupFailureCall()

        homeViewModel.onStartQuizClicked()

        verify { showProgressBarObserver.onChanged(false) }
    }

    @Test
    fun `Trigger failed event if failed to fetch questions`() {
        setupFailureCall()

        homeViewModel.onStartQuizClicked()

        verify { genericErrorObserver.onChanged(null) }
    }

    @Test
    fun `Hide progress bar if succeeded to fetch questions`() {
        setupSuccessCall()

        homeViewModel.onStartQuizClicked()

        verify { showProgressBarObserver.onChanged(false) }
    }

    @Test
    fun `Start quiz if succeeded to fetch questions`() {
        setupSuccessCall()

        homeViewModel.onStartQuizClicked()

        verify { startQuizObserver.onChanged(any()) }
    }

    @Test
    fun `Delegate to user auth repository to sign out when clicked to sign out`() {
        homeViewModel.onSignOutClicked()

        verify { userAuthRepository.logout() }
    }

    @Test
    fun `Trigger sign out event when clicked to sign out`() {
        homeViewModel.onSignOutClicked()

        verify { signOutObserver.onChanged(null) }
    }

    // region private

    private fun setupFetchQuestions() {
        coEvery { openTriviaRepository.fetchQuestions(any()) }.returns(any())
    }

    private fun setupFailureCall() {
        coEvery { openTriviaRepository.fetchQuestions(any()) }.throws(Exception())
    }

    private fun setupSuccessCall() {
        setupFetchQuestions()
    }

    // endregion
}