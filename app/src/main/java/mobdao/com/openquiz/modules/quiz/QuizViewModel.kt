package mobdao.com.openquiz.modules.quiz

import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import mobdao.com.openquiz.utils.pokos.ResultsReport
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.constants.BundleConstants.QUESTIONS
import mobdao.com.openquiz.utils.extensions.orZero
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel(), LifecycleObserver {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var showNextQuestionEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    var showResultsReportEvent: SingleLiveEvent<ResultsReport> = SingleLiveEvent()
    var questionNumberLiveData: MutableLiveData<Int> = MutableLiveData()

    private val answers: MutableList<String> by lazy {
        MutableList(questionsLiveData.value?.size.orZero()) { "" }
    }

    fun init(questions: List<Question>) {
        questionsLiveData.value = questions
    }

    fun onNextClicked(question: Question, answer: String) {
        val index = questionsLiveData.value?.indexOf(question) ?: return
        answers[index] = answer

        if (index + 1 == questionsLiveData.value?.size) {
            calculateFinalResult()
        } else {
            questionNumberLiveData.postValue(index + 1)
        }
    }

    fun onSaveInstanceState(outState: Bundle) = with(outState) {
        putParcelableArrayList(QUESTIONS, ArrayList(questionsLiveData.value ?: return))
    }

    fun onActivityCreated(savedInstanceState: Bundle?) = savedInstanceState?.run {
        questionsLiveData.value = getParcelableArrayList(QUESTIONS)
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
        showResultsReportEvent.postValue(
            ResultsReport(correctAnswers, wrongAnswers)
        )
    }

    //endregion

}