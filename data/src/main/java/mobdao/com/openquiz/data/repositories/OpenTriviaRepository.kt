package mobdao.com.openquiz.data.repositories

import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.webservices.QuestionsService
import mobdao.com.openquiz.data.server.webservices.SessionTokenService
import mobdao.com.openquiz.data.utils.extensions.toSingle
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.models.Question
import retrofit2.Retrofit
import javax.inject.Inject

@DataSingleton
class OpenTriviaRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val questionServiceMapper: QuestionServiceMapper
) {

    fun fetchSessionToken(): Single<String> =
        retrofit.create(SessionTokenService::class.java)
            .fetchSessionToken()
            .toSingle()
            .flatMap { sessionToken ->
                Single.just(sessionToken.token.orEmpty())
            }

    fun fetchQuestions(nOfQuestions: Int): Single<List<Question>> =
        retrofit.create(QuestionsService::class.java)
            .fetchQuestions(nOfQuestions)
            .toSingle()
            .flatMap { response ->
                Single.just(
                    // TODO handle response.response_code
                    questionServiceMapper.questionResponseToModel(response)
                )
            }
}