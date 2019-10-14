package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.constants.BundleConstants
import mobdao.com.openquiz.utils.extensions.orZero
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel() {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var questionNumberLiveData: MutableLiveData<Int> = MutableLiveData()

    private var answers: MutableList<String>? = null
    private var currentPage = 0

    fun init(questions: List<Question>) {
        questionsLiveData.postValue(questions)
        questionNumberLiveData.postValue(currentPage)
        answers = MutableList(questions.size) { "" }
    }

    fun onConfirmClicked(question: Question, answer: String) {
        val index = questionsLiveData.value?.indexOf(question) ?: return
        answers?.set(index, answer)

        if (index + 1 == questionsLiveData.value?.size) {
            calculateFinalResult()
        } else {
            questionNumberLiveData.postValue(questionNumberLiveData.value.orZero() + 1)
        }
    }

    fun onActivityCreated(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(BundleConstants.QUIZ_PAGE)?.let {
            currentPage = it
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