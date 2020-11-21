package mobdao.com.openquiz.modules.quiz

import androidx.lifecycle.MutableLiveData
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import mobdao.com.openquiz.utils.extensions.orZero
import mobdao.com.openquiz.utils.livedata.LiveEvent
import mobdao.com.openquiz.utils.pokos.ResultsReport

class QuizViewModel : BaseViewModel() {

    var questionsLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    var showNextQuestionEvent: LiveEvent<Unit> = LiveEvent()
    private var showCorrectAnswerEvents = mutableMapOf<Question, MutableLiveData<Boolean>>()
    private var showNextButtonEvents = mutableMapOf<Question, MutableLiveData<Boolean>>()
    private var showUnansweredQuestionEvents = mutableMapOf<Question, MutableLiveData<Boolean>>()
    private var runCountdownTimerEvents = mutableMapOf<Question, MutableLiveData<Boolean>>()
    private var selectedAnswerEvents = mutableMapOf<Question, MutableLiveData<Boolean>>()
    private var confirmAnswerEvents = mutableMapOf<Question, LiveEvent<Unit>>()

    private var game: Game? = null

    fun init(game: Game, questions: List<Question>) {
        this.game = game
        setupCorrectAnswerEvents(questions)
        questionsLiveData.postValue(questions)
    }

    fun onResume(question: Question) {
        runCountdownTimerEvents[question]?.value = true
    }

    fun onAnswerClicked(question: Question) {
        selectedAnswerEvents[question]?.value = true
    }

    fun onConfirmAnswerClicked(question: Question) {
        confirmAnswerEvents[question]?.call()
    }

    fun onConfirmAnswer(question: Question, answer: String) {
        game?.answer(question, answer)
        showCorrectAnswerEvents[question]?.value = true
        showNextButtonEvents[question]?.value = true
        runCountdownTimerEvents[question]?.value = false
    }

    fun onCountdownFinished(question: Question) {
        game?.answer(question, null)
        showUnansweredQuestionEvents[question]?.value = true
        showNextButtonEvents[question]?.value = true
    }

    fun onNextClicked() {
        val nextQuestion = game?.nextQuestion()

        if (nextQuestion == null) {
            showFinalResult()
        } else {
            showNextQuestionEvent.call()
        }
    }

    fun getShowCorrectAnswerEvent(question: Question): MutableLiveData<Boolean>? =
        showCorrectAnswerEvents[question]

    fun getShowNextButtonEvents(question: Question): MutableLiveData<Boolean>? =
        showNextButtonEvents[question]

    fun getShowUnansweredQuestionEvent(question: Question): MutableLiveData<Boolean>? =
        showUnansweredQuestionEvents[question]

    fun getRunCountdownTimerEvent(question: Question): MutableLiveData<Boolean>? =
        runCountdownTimerEvents[question]

    fun getSelectedAnswerEvent(question: Question): MutableLiveData<Boolean>? =
        selectedAnswerEvents[question]

    fun getConfirmAnswerEvent(question: Question): LiveEvent<Unit>? =
        confirmAnswerEvents[question]

    //region private

    private fun setupCorrectAnswerEvents(questions: List<Question>) {
        questions.forEach { question ->
            showCorrectAnswerEvents[question] = LiveEvent()
            showNextButtonEvents[question] = LiveEvent()
            showUnansweredQuestionEvents[question] = LiveEvent()
            runCountdownTimerEvents[question] = LiveEvent()
            selectedAnswerEvents[question] = LiveEvent()
            confirmAnswerEvents[question] = LiveEvent()
        }
    }

    private fun showFinalResult() {
        val correctAnswers = game?.getNumberOfCorrectAnswers().orZero()
        val wrongAnswers = game?.getNumberOfIncorrectAnswers().orZero()
        val resultsReport = ResultsReport(correctAnswers, wrongAnswers)
        routeEvent.value = QuizFragmentDirections.toResultsReportFragment(
            resultsReport
        )
    }

    //endregion
}
