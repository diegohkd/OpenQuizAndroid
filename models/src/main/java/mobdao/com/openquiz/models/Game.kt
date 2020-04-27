package mobdao.com.openquiz.models

class Game(private val questions: List<Question>) {

    private var questionIndex = 0
    private var questionsAnsweredIncorrectly = 0

    var score: Int = 0

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    fun answer(question: Question, option: String) {
        val isCorrectAnswer = question.answer(option)
        if (isCorrectAnswer) {
            score++
        } else {
            questionsAnsweredIncorrectly++
        }
    }

    fun getNumberOfCorrectAnswers(): Int = score
    fun getNumberOfIncorrectAnswers(): Int = questionsAnsweredIncorrectly
}