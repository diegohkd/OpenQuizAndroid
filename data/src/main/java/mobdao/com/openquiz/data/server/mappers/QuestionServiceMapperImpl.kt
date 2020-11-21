package mobdao.com.openquiz.data.server.mappers

import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Question

class QuestionServiceMapperImpl : QuestionServiceMapper {

    override fun questionResponseToModel(questionsResponse: QuestionsResponse): List<Question> =
        questionsResponse.results.map {
            with(it) {
                Question(
                    category = category ?: throw RuntimeException("category can not be null"),
                    type = type ?: throw RuntimeException("type can not be null"),
                    difficulty = difficulty ?: throw RuntimeException("difficulty can not be null"),
                    question = question ?: throw RuntimeException("question can not be null"),
                    correctAnswer = correct_answer
                        ?: throw RuntimeException("correctAnswer can not be null"),
                    incorrectAnswers = incorrect_answers
                        ?: throw RuntimeException("incorrectAnswers can not be null")
                )
            }
        }
}
