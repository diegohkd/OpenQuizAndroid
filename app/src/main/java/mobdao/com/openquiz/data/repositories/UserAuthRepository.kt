package mobdao.com.openquiz.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun isUserLoggedIn(): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            postValue(FirebaseAuth.getInstance().currentUser != null)
        }
}