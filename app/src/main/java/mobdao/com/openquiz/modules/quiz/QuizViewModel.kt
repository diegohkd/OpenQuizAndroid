package mobdao.com.openquiz.modules.quiz

import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.extensions.orZero
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent
import mobdao.com.openquiz.utils.pokos.ResultsReport
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel() {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var showNextQuestionEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    var showResultsReportEvent: SingleLiveEvent<ResultsReport> = SingleLiveEvent()
    private var showCorrectAnswerEvents = mutableMapOf<Question, SingleLiveEvent<Unit>>()

    private var game: Game? = null

    fun init(questions: List<Question>) {
        game = Game(questions)
        setupCorrectAnswerEvents(questions)
        questionsLiveData.postValue(questions)
    }

    fun onConfirmAnswerClicked(question: Question, answer: String) {
        game?.answer(question, answer)
        showCorrectAnswerEvents[question]?.call()
    }

    fun onNextClicked() {
        val nextQuestion = game?.nextQuestion()

        if (nextQuestion == null) {
            showFinalResult()
        } else {
            showNextQuestionEvent.call()
        }
    }

    fun getShowCorrectAnswerEvent(question: Question): SingleLiveEvent<Unit>? {
        return showCorrectAnswerEvents[question]
    }

    //region private

    private fun setupCorrectAnswerEvents(questions: List<Question>) {
        questions.forEach { question -> showCorrectAnswerEvents[question] = SingleLiveEvent() }

    }

    private fun showFinalResult() {
        val correctAnswers = game?.getNumberOfCorrectAnswers().orZero()
        val wrongAnswers = game?.getNumberOfIncorrectAnswers().orZero()
        showResultsReportEvent.postValue(ResultsReport(correctAnswers, wrongAnswers))
    }

    //endregion

}