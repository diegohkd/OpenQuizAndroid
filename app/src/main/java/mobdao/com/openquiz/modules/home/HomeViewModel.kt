package mobdao.com.openquiz.modules.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    private val openTriviaRepository: OpenTriviaRepository
) : BaseViewModel() {

    val startQuizEvent: SingleLiveEvent<List<Question>> = SingleLiveEvent()
    val signOutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private var nOfQuestions = 10

    fun onStartQuizClicked() = viewModelScope.launch {
        showProgressBar()
        runCatching {
            openTriviaRepository.fetchQuestions(nOfQuestions)
        }.onSuccess { questions ->
            hideProgressBar()
            startQuizEvent.postValue(questions)
        }.onFailure {
            hideProgressBar()
            genericErrorEvent.call()
        }
    }

    fun onSignOutClicked() {
        viewModelScope.cancel()
        userAuthRepository.logout()
        signOutEvent.call()
    }
}