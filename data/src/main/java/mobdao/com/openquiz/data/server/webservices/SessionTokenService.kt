package mobdao.com.openquiz.data.server.webservices

import mobdao.com.openquiz.data.server.responses.SessionTokenResponse
import retrofit2.http.GET

interface SessionTokenService {

    @GET("api_token.php?command=request")
    suspend fun fetchSessionToken(): SessionTokenResponse
}
