package mobdao.com.openquiz.data.server.mappers

import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.responses.QuestionsResponse
import mobdao.com.openquiz.models.Category
import mobdao.com.openquiz.models.Category.*
import mobdao.com.openquiz.models.Difficulty
import mobdao.com.openquiz.models.Difficulty.*
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType
import mobdao.com.openquiz.models.QuestionType.MULTIPLE_CHOICE
import mobdao.com.openquiz.models.QuestionType.TRUE_FALSE
import javax.inject.Inject

@DataSingleton
class QuestionServiceMapper @Inject constructor() {

    fun questionResponseToModel(
        questionsResponse: QuestionsResponse
    ): List<Question> {
        val questions = mutableListOf<Question>()
        questionsResponse.results.forEach {
            with(it) {
                questions.add(
                    Question(
                        category = getCategory(category),
                        type = getType(type),
                        difficulty = getDifficulty(difficulty),
                        question = question,
                        correctAnswer = correct_answer,
                        incorrectAnswers = incorrect_answers
                    )
                )
            }
        }
        return questions
    }

    //region private

    private fun getCategory(categoryRaw: String?): Category? {
        return when (categoryRaw) {
            "General Knowledge" -> GENERAL_KNOWLEDGE
            "Entertainment: Books" -> ENTERTAINMENT_BOOKS
            "Entertainment: Film" -> ENTERTAINMENT_FILM
            "Entertainment: Music" -> ENTERTAINMENT_MUSIC
            "Entertainment: Musicals & Theatres" -> ENTERTAINMENT_MUSICALS_AND_THEATRES
            "Entertainment: Television" -> ENTERTAINMENT_TELEVISION
            "Entertainment: Video Games" -> ENTERTAINMENT_VIDEO_GAMES
            "Entertainment: Board Games" -> ENTERTAINMENT_BOARD_GAMES
            "Science & Nature" -> SCIENCE_AND_NATURE
            "Science: Computers" -> SCIENCE_COMPUTERS
            "Science: Mathematics" -> SCIENCE_MATHEMATICS
            "Mythology" -> MYTHOLOGY
            "Sports" -> SPORTS
            "Geography" -> GEOGRAPHY
            "History" -> HISTORY
            "Politics" -> POLITICS
            "Art" -> ART
            "Celebrities" -> CELEBRITIES
            "Animals" -> ANIMALS
            "Vehicles" -> VEHICLES
            "Entertainment: Comics" -> ENTERTAINMENT_COMICS
            "Science: Gadgets" -> SCIENCE_GADGETS
            "Entertainment: Japanese Anime & Manga" -> ENTERTAINMENT_JAPANESE_ANIME_AND_MANGA
            "Entertainment: Cartoon & Animations" -> ENTERTAINMENT_CARTOON_AND_ANIMATIONS
            else -> null
        }
    }

    private fun getType(typeRaw: String?): QuestionType? {
        return when (typeRaw) {
            "multiple" -> MULTIPLE_CHOICE
            "boolean" -> TRUE_FALSE
            else -> null
        }

    }

    private fun getDifficulty(difficultyRaw: String?): Difficulty? {
        return when (difficultyRaw) {
            "easy" -> EASY
            "medium" -> NORMAL
            "hard" -> HARD
            else -> null
        }

    }

    //endregion
}