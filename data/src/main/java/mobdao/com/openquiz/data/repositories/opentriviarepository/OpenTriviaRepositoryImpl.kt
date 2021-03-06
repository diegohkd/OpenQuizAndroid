package mobdao.com.openquiz.data.repositories.opentriviarepository

import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.server.webservices.SessionTokenService
import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode
import mobdao.com.openquiz.data.utils.exceptions.QuestionsException
import mobdao.com.openquiz.models.Question
import retrofit2.Retrofit

class OpenTriviaRepositoryImpl(
    private val retrofit: Retrofit,
    private val questionServiceMapper: QuestionServiceMapper
) : OpenTriviaRepository {

    override suspend fun fetchSessionToken(): String = retrofit
        .create(SessionTokenService::class.java)
        .fetchSessionToken().token.orEmpty()

    override suspend fun fetchQuestions(nOfQuestions: Int): List<Question> {
        val response = retrofit
            .create(QuestionsService::class.java)
            .fetchQuestions(nOfQuestions)
        if (response.response_code != QuestionsResponseCode.SUCCESS.code) {
            throw QuestionsException(
                "Failed to fetch questions",
                QuestionsResponseCode.from(response.response_code)
            )
        }
        return questionServiceMapper.questionResponseToModel(response)
    }
}
