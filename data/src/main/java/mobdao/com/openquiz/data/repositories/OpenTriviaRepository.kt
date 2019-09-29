package mobdao.com.openquiz.data.repositories

import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.webservices.SessionTokenService
import mobdao.com.openquiz.data.utils.BaseSingle
import mobdao.com.openquiz.data.utils.toBaseSingle
import retrofit2.Retrofit
import javax.inject.Inject

@DataSingleton
class OpenTriviaRepository @Inject constructor(
    private val retrofit: Retrofit
) {

    fun fetchSessionToken(): BaseSingle<String> =
        retrofit.create(SessionTokenService::class.java)
            .fetchSessionToken()
            .toBaseSingle()
            .flatMap { sessionToken ->
                BaseSingle.just(sessionToken.token.orEmpty())
            }
}