package mobdao.com.openquiz.data.server.webservices

import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsService {

    @GET("api.php")
    suspend fun fetchQuestions(@Query("amount") nOfQuestions: Int): QuestionsResponse
}
