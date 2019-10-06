package mobdao.com.openquiz.modules.quiz

import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel() {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var showNextQuestionEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun init(questions: List<Question>) {
        questionsLiveData.postValue(questions)
    }

    fun onConfirmClicked() {
        showNextQuestionEvent.call()
    }

}