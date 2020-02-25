package mobdao.com.openquiz.data.server.mappers

import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Question

interface QuestionServiceMapper {
    fun questionResponseToModel(questionsResponse: QuestionsResponse): List<Question>
}