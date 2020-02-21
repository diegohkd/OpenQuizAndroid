package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import javax.inject.Inject

@DataSingleton
class UserAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserAuthRepository {

    override fun isUserLoggedIn(): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            postValue(FirebaseAuth.getInstance().currentUser != null)
        }

    override fun loginOnFirebase(
        account: GoogleSignInAccount,
        success: () -> Unit,
        failure: (Exception?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success()
                } else {
                    failure(task.exception)
                }
            }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}