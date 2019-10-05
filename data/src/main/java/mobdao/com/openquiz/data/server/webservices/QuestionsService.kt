package mobdao.com.openquiz.data.server.webservices

import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsService {

    @GET("api.php?amount=10")
    fun fetchQuestions(
        @Query("amount") nOfQuestions: Int
    ): Call<QuestionsResponse>
}