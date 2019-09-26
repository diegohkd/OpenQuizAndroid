package mobdao.com.openquiz.data.repositories

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenTriviaRepository @Inject constructor(
    val retrofit: Retrofit
) {
}