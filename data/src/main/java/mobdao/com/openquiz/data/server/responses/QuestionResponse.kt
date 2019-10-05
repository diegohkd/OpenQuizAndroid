package mobdao.com.openquiz.data.server.responses

data class QuestionResponse(
    val category: String?,
    val type: String?,
    val difficulty: String?,
    val question: String?,
    val correct_answer: String?,
    val incorrect_answers: List<String>?
)