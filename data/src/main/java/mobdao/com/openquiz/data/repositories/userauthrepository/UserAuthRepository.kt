package mobdao.com.openquiz.data.repositories.userauthrepository

import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface UserAuthRepository {
    fun isUserLoggedIn(): LiveData<Boolean>
    fun loginOnFirebase(
        account: GoogleSignInAccount,
        success: () -> Unit,
        failure: (Exception?) -> Unit
    )
    fun logout()
}