package mobdao.com.openquiz.data.server.responses

data class QuestionsResponse(
    val response_code: Int?,
    val results: List<QuestionResponse>
)