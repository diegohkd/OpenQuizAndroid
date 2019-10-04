package mobdao.com.openquiz.data.server.responses

data class QuestionsResponse(
    val response_code: String?,
    val results: List<QuestionResponse>
)