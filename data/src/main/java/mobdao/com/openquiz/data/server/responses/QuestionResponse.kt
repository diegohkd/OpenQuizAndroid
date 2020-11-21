package mobdao.com.openquiz.data.server.responses

import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.QuestionType

data class QuestionResponse(
    val category: Category?,
    val type: QuestionType?,
    val difficulty: Difficulty?,
    val question: String?,
    val correct_answer: String?,
    val incorrect_answers: List<String>?
)
