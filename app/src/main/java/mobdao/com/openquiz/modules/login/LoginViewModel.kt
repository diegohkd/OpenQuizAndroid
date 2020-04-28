package mobdao.com.openquiz.modules.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.constants.RequestCodeConstants
import mobdao.com.openquiz.utils.livedata.LiveEvent

class LoginViewModel(
    private val userAuthRepository: UserAuthRepository
) : BaseViewModel() {

    val showHomeScreenEvent: LiveEvent<Unit> = LiveEvent()
    val showGoogleSignInEvent: LiveEvent<Unit> = LiveEvent()

    fun onGoogleSignInClicked() {
        showProgressBar()
        showGoogleSignInEvent.call()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCodeConstants.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            onBackFromGoogleSignIn(data)
        } else {
            onError()
        }
    }

    //region private

    private fun onBackFromGoogleSignIn(data: Intent?) {
        val account = try {
            val googleSignInTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleSignInTask.getResult(ApiException::class.java)
        } catch (exception: ApiException) {
            null
        }
        account?.let(::loginOnFirebase) ?: onError()
    }

    private fun loginOnFirebase(account: GoogleSignInAccount) = viewModelScope.launch {
        runCatching {
            userAuthRepository.loginOnFirebase(account)
        }.onSuccess {
            hideProgressBar()
            showHomeScreenEvent.call()
        }.onFailure {
            onError()
        }
    }

    private fun onError() {
        hideProgressBar()
        genericErrorEvent.postValue(Unit)
    }

    //endregion
}