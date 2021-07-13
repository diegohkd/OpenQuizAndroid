package mobdao.com.openquiz.data.repositories.opentriviarepository

import mobdao.com.openquiz.models.Question

interface OpenTriviaRepository {
    suspend fun fetchQuestions(nOfQuestions: Int): List<Question>
}
