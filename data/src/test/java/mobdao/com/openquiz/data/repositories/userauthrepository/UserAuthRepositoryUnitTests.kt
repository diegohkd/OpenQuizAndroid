package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.utils.extensions.orFalse
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserAuthRepositoryUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var currentUser: FirebaseUser
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    @Mock
    private lateinit var taskCallback: Task<Task<AuthResult>>
    @Mock
    private lateinit var signInTask: Task<AuthResult>
    @Mock
    private lateinit var googleSignInAccount: GoogleSignInAccount
    @Mock
    private lateinit var successCallback: () -> Unit
    @Mock
    private lateinit var failureCallback: (Exception?) -> Unit
    @Mock
    private lateinit var authCredential: AuthCredential
    @Mock
    private lateinit var googleAuthProvider: GoogleAuthProvider

    private lateinit var repository: UserAuthRepository

    @Before
    fun setup() {
        repository = UserAuthRepositoryImpl(firebaseAuth, googleAuthProvider)
    }

    @Test
    fun `Returns true when user is logged in`() {
        whenever(firebaseAuth.getCurrentUser()).thenReturn(currentUser)

        val result = repository.isUserLoggedIn()

        assertTrue(result.value.orFalse())
    }

    @Test
    fun `Returns false when user is not logged in`() {
        whenever(firebaseAuth.getCurrentUser()).thenReturn(null)

        val result = repository.isUserLoggedIn()

        assertFalse(result.value.orFalse())
    }

    @Test
    fun `Returns success when succeeded to login on Firebase`() {
        setupAuthCallWithSuccessCallback()

        repository.loginOnFirebase(googleSignInAccount, successCallback, failureCallback)

        verify(successCallback)()
    }

    @Test
    fun `Returns failure when failed to login on Firebase`() {
        setupAuthCallWithFailureCallback()

        repository.loginOnFirebase(googleSignInAccount, successCallback, failureCallback)

        verify(failureCallback)(null)
    }

    @Test
    fun `Delegate sign out to Firebase when user signs out`() {
        repository.logout()

        verify(firebaseAuth).signOut()
    }

    // region private

    private fun setupAuthCallWithSuccessCallback() {
        defaultAuthCallSetup()
        whenever(taskCallback.isSuccessful).thenReturn(true)
    }

    private fun setupAuthCallWithFailureCallback() {
        defaultAuthCallSetup()
        whenever(taskCallback.exception).thenReturn(null)
        whenever(taskCallback.isSuccessful).thenReturn(false)
    }

    private fun defaultAuthCallSetup() {
        whenever(googleSignInAccount.idToken).thenReturn("")
        whenever(googleAuthProvider.getCredential(any())).thenReturn(authCredential)
        whenever(firebaseAuth.signInWithCredential(authCredential)).thenReturn(signInTask)
        doAnswer {
            val callback: OnCompleteListener<Task<AuthResult>> = it.getArgument(0)
            callback.onComplete(taskCallback)
            taskCallback
        }.whenever(signInTask).addOnCompleteListener(any())
    }

    // endregion
}