package mobdao.com.openquiz.data.repositories.opentriviarepository

import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.models.Question

interface OpenTriviaRepository {
    fun fetchSessionToken(): Single<String>
    fun fetchQuestions(nOfQuestions: Int): Single<List<Question>>
}