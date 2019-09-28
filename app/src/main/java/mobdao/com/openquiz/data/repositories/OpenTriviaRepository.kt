package mobdao.com.openquiz.data.repositories

import mobdao.com.openquiz.data.server.responses.SessionTokenResponse
import mobdao.com.openquiz.data.server.webservices.SessionTokenService
import mobdao.com.openquiz.data.utils.Single
import mobdao.com.openquiz.data.utils.toSingle
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenTriviaRepository @Inject constructor(
    private val retrofit: Retrofit
) {

    fun fetchSessionToken(): Single<SessionTokenResponse> =
        retrofit.create(SessionTokenService::class.java)
            .fetchSessionToken()
            .toSingle()
}