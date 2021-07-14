package mobdao.com.openquiz.modules.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(userAuthRepository: UserAuthRepository) : ViewModel() {
    val isUserLoggedInLiveData: LiveData<Boolean> = userAuthRepository.isUserLoggedIn()
}
