package mobdao.com.openquiz.modules.home

import androidx.lifecycle.ViewModel
import mobdao.com.openquiz.data.repositories.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.UserAuthRepository
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    private val openTriviaRepository: OpenTriviaRepository
) : ViewModel() {

    val signOutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun onSignOutClicked() {
        userAuthRepository.logout()
        signOutEvent.call()
    }
}