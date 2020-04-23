package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.utils.extensions.orFalse
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserAuthRepositoryUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var currentUser: FirebaseUser

    @MockK
    private lateinit var firebaseAuth: FirebaseAuth

    @MockK
    private lateinit var signInTask: Task<AuthResult>

    @MockK
    private lateinit var authResult: AuthResult

    @MockK
    private lateinit var googleSignInAccount: GoogleSignInAccount

    @MockK
    private lateinit var authCredential: AuthCredential

    @MockK
    private lateinit var googleAuthProvider: GoogleAuthProvider

    private lateinit var repository: UserAuthRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        repository = UserAuthRepositoryImpl(firebaseAuth, googleAuthProvider)
    }

    @Test
    fun `Returns true when user is logged in`() {
        every { firebaseAuth.getCurrentUser() }.returns(currentUser)

        val result = repository.isUserLoggedIn()

        assertTrue(result.value.orFalse())
    }

    @Test
    fun `Returns false when user is not logged in`() {
        every { firebaseAuth.getCurrentUser() }.returns(null)

        val result = repository.isUserLoggedIn()

        assertFalse(result.value.orFalse())
    }

    @Test
    fun `Returns success when succeeded to login on Firebase`() = runBlockingTest {
        setupAuthCallWithSuccessCallback()

        var result = false
        runCatching {
            repository.loginOnFirebase(googleSignInAccount)
        }.onSuccess {
            result = true
        }

        assert(result)
    }

    @Test
    fun `Returns failure when failed to login on Firebase`() = runBlockingTest {
        setupAuthCallWithFailureCallback()

        var result = true
        runCatching {
            repository.loginOnFirebase(googleSignInAccount)
        }.onFailure {
            result = false
        }

        assert(!result)
    }

    @Test
    fun `Delegate sign out to Firebase when user signs out`() {
        repository.logout()

        verify(exactly = 1) { firebaseAuth.signOut() }
    }

    // region private

    private fun setupAuthCallWithSuccessCallback() {
        defaultAuthCallSetup()
        coEvery { signInTask.await() } returns authResult
    }

    private fun setupAuthCallWithFailureCallback() {
        defaultAuthCallSetup()
        coEvery { signInTask.await() } throws Exception()
    }

    private fun defaultAuthCallSetup() {
        every { googleSignInAccount.idToken }.returns("")
        every { googleAuthProvider.getCredential(any()) }.returns(authCredential)
        every { firebaseAuth.signInWithCredential(authCredential) }.returns(signInTask)
    }

    // endregion
}