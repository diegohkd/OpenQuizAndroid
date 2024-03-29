package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.tasks.await
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleAuthProvider: GoogleAuthProvider
) : UserAuthRepository {

    override fun isUserLoggedIn(): LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(firebaseAuth.getCurrentUser() != null)
    }

    override suspend fun loginOnFirebase(account: GoogleSignInAccount) {
        val credential = googleAuthProvider.getCredential(account.idToken)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
