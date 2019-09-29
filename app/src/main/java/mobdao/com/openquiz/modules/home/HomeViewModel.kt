package mobdao.com.openquiz.modules.home

import android.util.Log
import androidx.lifecycle.ViewModel
import mobdao.com.openquiz.data.repositories.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.UserAuthRepository
import mobdao.com.openquiz.data.utils.Callback
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    openTriviaRepository: OpenTriviaRepository
) : ViewModel() {

    val signOutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    init {
        openTriviaRepository.fetchSessionToken()
            .subscribeBy(object : Callback<String> {
                override fun onSuccess(result: String) {
                    Log.d("HomeViewModel", "token: $result")
                }

                override fun onFailure(exception: Throwable?) {
                    exception?.printStackTrace()
                }
            })
    }

    fun onSignOutClicked() {
        userAuthRepository.logout()
        signOutEvent.call()
    }
}