package mobdao.com.openquiz.data.server.mappers

import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Question
import javax.inject.Inject

@DataSingleton
class QuestionServiceMapper @Inject constructor() {

    fun questionResponseToModel(
        questionsResponse: QuestionsResponse
    ): List<Question> =
        questionsResponse.results.map {
            with(it) {
                Question(
                    category = category,
                    type = type,
                    difficulty = difficulty,
                    question = question,
                    correctAnswer = correct_answer,
                    incorrectAnswers = incorrect_answers
                )
            }
        }
}