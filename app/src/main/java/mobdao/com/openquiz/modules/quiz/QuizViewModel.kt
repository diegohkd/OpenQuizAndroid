package mobdao.com.openquiz.modules.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel() {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var showNextQuestionEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private var answers: MutableList<String>? = null

    fun init(questions: List<Question>) {
        questionsLiveData.postValue(questions)
        answers = MutableList(questions.size) { "" }
    }

    fun onNextClicked(question: Question, answer: String) {
        val index = questionsLiveData.value?.indexOf(question) ?: return
        answers?.set(index, answer)

        if (index + 1 == questionsLiveData.value?.size) {
            calculateFinalResult()
        } else {
            showNextQuestionEvent.call()
        }
    }

    //region private

    private fun calculateFinalResult() {
        var correctAnswers = 0
        var wrongAnswers = 0
        questionsLiveData.value?.forEachIndexed { index, question ->
            if (question.correctAnswer == answers?.get(index))
                correctAnswers++
            else
                wrongAnswers++
        }
        Log.d("aaaa", "correctAnswers: $correctAnswers /n wrongAnswers: $wrongAnswers")
    }

    //endregion

}