package mobdao.com.openquiz.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserAuthRepository @Inject constructor() {

    fun isUserLoggedIn(): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            postValue(FirebaseAuth.getInstance().currentUser != null)
        }
}