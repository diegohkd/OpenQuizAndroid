package mobdao.com.openquiz.modules.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.modules.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    private val openTriviaRepository: OpenTriviaRepository
) : BaseViewModel() {

    private var totalOfQuestions = 10

    fun onStartQuizClicked() {
        viewModelScope.launch {
            showProgressBar()
            runCatching {
                openTriviaRepository.fetchQuestions(totalOfQuestions)
            }.onSuccess { questions ->
                hideProgressBar()
                routeEvent.value = HomeFragmentDirections.toQuizFragment(questions.toTypedArray())
            }.onFailure {
                hideProgressBar()
                genericErrorEvent.call()
            }
        }
    }

    fun onSignOutClicked() {
        viewModelScope.cancel()
        userAuthRepository.logout()
        routeEvent.value = HomeFragmentDirections.toLoginFragment()
    }
}
