package mobdao.com.openquiz.modules.login

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import mobdao.com.openquiz.data.repositories.UserAuthRepository
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.constants.RequestCodeConstants
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) : BaseViewModel() {

    val showHomeScreenEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCodeConstants.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            onBackFromGoogleSignIn(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    //region private

    private fun onBackFromGoogleSignIn(googleSignInTask: Task<GoogleSignInAccount>) {
        try {
            googleSignInTask.getResult(ApiException::class.java)
        } catch (exception: ApiException) {
            null
        }?.apply {
            loginOnFirebase(this)
        } ?: errorEvent.call()
    }

    private fun loginOnFirebase(account: GoogleSignInAccount) {
        userAuthRepository.loginOnFirebase(
            account = account,
            success = {
                showHomeScreenEvent.call()
            },
            failure = { exception ->
                exception?.printStackTrace()
                errorEvent.call()
            }
        )
    }

    //endregion
}