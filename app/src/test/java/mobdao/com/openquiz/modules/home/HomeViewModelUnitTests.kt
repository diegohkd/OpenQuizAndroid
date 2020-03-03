package mobdao.com.openquiz.modules.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.models.Question
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

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
    @MockK
    private lateinit var single: Single<List<Question>>
    @MockK
    private lateinit var disposable: Disposable

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(userAuthRepository, openTriviaRepository, disposable)
        homeViewModel.showProgressBarEvent.observeForever(showProgressBarObserver)
        homeViewModel.genericErrorEvent.observeForever(genericErrorObserver)
        homeViewModel.startQuizEvent.observeForever(startQuizObserver)
        homeViewModel.signOutEvent.observeForever(signOutObserver)
    }

    @Test
    fun `Show progress bar when clicked to start quiz`() {
        setupSingleSubscription()

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

    @Test
    fun `Dispose disposable when clicked to sign out`() {
        homeViewModel.onSignOutClicked()

        verify { disposable.dispose() }
    }

    @Test
    fun `Dispose disposable when onCleared() called`() {
        homeViewModel.clear()

        verify { disposable.dispose() }
    }

    // region private

    private fun setupSingleSubscription() {
        every { openTriviaRepository.fetchQuestions(any()) }.returns(single)
        every { single.subscribeBy(any()) }.returns(disposable)
    }

    private fun setupFailureCall() {
        setupSingleSubscription()
        every { single.subscribeBy(any()) }.answers {
            firstArg<Callback<List<Question>>>().failure?.invoke(null)
            disposable
        }
    }

    private fun setupSuccessCall() {
        setupSingleSubscription()
        every { single.subscribeBy(any()) }.answers {
            firstArg<Callback<List<Question>>>().success?.invoke(emptyList())
            disposable
        }
    }

    // endregion
}