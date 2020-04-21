package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface UserAuthRepository {
    fun isUserLoggedIn(): LiveData<Boolean>
    suspend fun loginOnFirebase(account: GoogleSignInAccount)
    fun logout()
}