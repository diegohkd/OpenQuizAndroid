package mobdao.com.openquiz.modules.home

import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository,
    private val openTriviaRepository: OpenTriviaRepository,
    private var disposable: Disposable? = null
) : BaseViewModel() {

    val startQuizEvent: SingleLiveEvent<List<Question>> = SingleLiveEvent()
    val signOutEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private var nOfQuestions = 10

    fun onStartQuizClicked() {
        showProgressBar()
        disposable = openTriviaRepository.fetchQuestions(nOfQuestions)
            .subscribeBy(
                Callback({ questions ->
                    hideProgressBar()
                    startQuizEvent.postValue(questions)
                }, {
                    hideProgressBar()
                    genericErrorEvent.call()
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

    @TestOnly
    fun clear() {
        onCleared()
    }
}