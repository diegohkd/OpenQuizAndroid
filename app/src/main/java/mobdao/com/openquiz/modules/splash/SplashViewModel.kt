package mobdao.com.openquiz.modules.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mobdao.com.openquiz.data.repositories.UserAuthRepository

class SplashViewModel(
    private val userAuthRepository: UserAuthRepository = UserAuthRepository()
) : ViewModel() {

    val isUserLoggedInLiveData: LiveData<Boolean> = userAuthRepository.isUserLoggedIn()
}