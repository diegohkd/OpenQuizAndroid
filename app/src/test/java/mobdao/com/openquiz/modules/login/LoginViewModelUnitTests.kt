package mobdao.com.openquiz.modules.login

import android.app.Activity
import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.mockk.*
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.utils.constants.RequestCodeConstants.RC_SIGN_IN
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var userAuthRepository: UserAuthRepository
    @MockK
    private lateinit var showGoogleSignInObserver: Observer<Unit>
    @MockK
    private lateinit var showProgressBarObserver: Observer<Boolean>
    @MockK
    private lateinit var errorObserver: Observer<Unit>
    @MockK
    private lateinit var showHomeScreenObserver: Observer<Unit>
    @MockK
    private lateinit var intent: Intent
    @MockK
    private lateinit var googleSignInTask: Task<GoogleSignInAccount>
    @MockK
    private lateinit var apiException: ApiException
    @MockK
    private lateinit var googleSignInAccount: GoogleSignInAccount

    private lateinit var loginViewModel: LoginViewModel
    private val unknownRequestCode = 0
    private val unexpectedResultCode = 0

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic(GoogleSignIn::class)
        loginViewModel = LoginViewModel(userAuthRepository)

        loginViewModel.showGoogleSignInEvent.observeForever(showGoogleSignInObserver)
        loginViewModel.showProgressBarEvent.observeForever(showProgressBarObserver)
        loginViewModel.errorEvent.observeForever(errorObserver)
        loginViewModel.showHomeScreenEvent.observeForever(showHomeScreenObserver)
    }

    @Test
    fun `Show progress bar when clicked on google sign in`() {
        loginViewModel.onGoogleSignInClicked()

        verify { showProgressBarObserver.onChanged(true) }
    }

    @Test
    fun `Trigger show sign in live event when clicked on google sign in`() {
        loginViewModel.onGoogleSignInClicked()

        verify { showGoogleSignInObserver.onChanged(null) }
    }

    @Test
    fun `Hide progress bar when back from google sign in with unknown requestCode`() {
        loginViewModel.onActivityResult(unknownRequestCode, unexpectedResultCode, intent)

        verify { showProgressBarObserver.onChanged(false) }
    }

    @Test
    fun `Trigger error event when back from google sign in with unknown requestCode`() {
        loginViewModel.onActivityResult(unknownRequestCode, unexpectedResultCode, intent)

        verify { errorObserver.onChanged(null) }
    }

    @Test
    fun `Hide progress bar when back from google sign in with resultCode other than RESULT_OK`() {
        loginViewModel.onActivityResult(RC_SIGN_IN, unexpectedResultCode, intent)

        verify { showProgressBarObserver.onChanged(false) }
    }

    @Test
    fun `Trigger error event when back from google sign in with resultCode other than RESULT_OK`() {
        loginViewModel.onActivityResult(RC_SIGN_IN, unexpectedResultCode, intent)

        verify { errorObserver.onChanged(null) }
    }

    @Test
    fun `Throw ApiException when failed to google sign in`() {
        every { GoogleSignIn.getSignedInAccountFromIntent(any()) }.returns(googleSignInTask)
        every { googleSignInTask.getResult(ApiException::class.java) }.throws(apiException)
        every { apiException.statusCode }.returns(0)
        val loginViewModelMock = spyk(loginViewModel, recordPrivateCalls = true)
        every { loginViewModelMock["onError"]() } returns Unit

        loginViewModelMock.onActivityResult(RC_SIGN_IN, Activity.RESULT_OK, intent)

        verify { loginViewModelMock["onError"]() }
    }

    @Test
    fun `Delegate login to repository when valid google account is obtained`() {
        setupGoogleSignInAccount()

        loginViewModel.onActivityResult(RC_SIGN_IN, Activity.RESULT_OK, intent)

        verify(exactly = 1) { userAuthRepository.loginOnFirebase(any(), any(), any()) }
    }

    @Test
    fun `Call onError() if failed to login using repository`() {
        setupFailureSignIn()
        val loginViewModelMock = spyk(loginViewModel, recordPrivateCalls = true)
        every { loginViewModelMock["onError"]() } returns Unit

        loginViewModelMock.onActivityResult(RC_SIGN_IN, Activity.RESULT_OK, intent)

        verify { loginViewModelMock["onError"]() }
    }

    @Test
    fun `Hide progress bar if succeeded to login using repository`() {
        setupSuccessSignIn()

        loginViewModel.onActivityResult(RC_SIGN_IN, Activity.RESULT_OK, intent)

        verify { showProgressBarObserver.onChanged(false) }
    }

    @Test
    fun `Trigger show home screen event if succeeded to login using repository`() {
        setupSuccessSignIn()

        loginViewModel.onActivityResult(RC_SIGN_IN, Activity.RESULT_OK, intent)

        verify { showHomeScreenObserver.onChanged(null) }
    }

    private fun setupGoogleSignInAccount() {
        every { GoogleSignIn.getSignedInAccountFromIntent(any()) }.returns(googleSignInTask)
        every { googleSignInTask.getResult(ApiException::class.java) }.returns(googleSignInAccount)
    }

    private fun setupSuccessSignIn() {
        setupGoogleSignInAccount()
        every { userAuthRepository.loginOnFirebase(any(), any(), any()) }.answers {
            secondArg<() -> Unit>().invoke()
        }
    }

    private fun setupFailureSignIn() {
        setupGoogleSignInAccount()
        every { userAuthRepository.loginOnFirebase(any(), any(), any()) }.answers {
            thirdArg<(Exception?) -> Unit>().invoke(null)
        }
    }
}