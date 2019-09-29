package mobdao.com.openquiz.data.repositories

import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.webservices.SessionTokenService
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.data.utils.extensions.toSingle
import retrofit2.Retrofit
import javax.inject.Inject

@DataSingleton
class OpenTriviaRepository @Inject constructor(
    private val retrofit: Retrofit
) {

    fun fetchSessionToken(): Single<String> =
        retrofit.create(SessionTokenService::class.java)
            .fetchSessionToken()
            .toSingle()
            .flatMap { sessionToken ->
                Single.just(sessionToken.token.orEmpty())
            }
}