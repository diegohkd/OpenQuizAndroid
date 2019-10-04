package mobdao.com.openquiz.models

data class Question(
    val category: Category?,
    val type: QuestionType?,
    val difficulty: Difficulty?,
    val question: String?,
    val correctAnswer: String?,
    val incorrectAnswers: List<String>?
)