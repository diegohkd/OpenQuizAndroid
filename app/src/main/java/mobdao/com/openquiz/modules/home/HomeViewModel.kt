package mobdao.com.openquiz.modules.home

import android.util.Log
import mobdao.com.openquiz.data.repositories.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.UserAuthRepository
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    openTriviaRepository: OpenTriviaRepository
) : BaseViewModel() {

    val signOutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    var disposable: Disposable? = null

    init {
        disposable = openTriviaRepository.fetchSessionToken()
            .subscribeBy(
                Callback({ result ->
                    Log.d("HomeViewModel", "token: $result")
                }, { exception ->
                    exception?.printStackTrace()
                })
            )
    }

    fun onSignOutClicked() {
        disposable?.dispose()
        userAuthRepository.logout()
        signOutEvent.call()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}