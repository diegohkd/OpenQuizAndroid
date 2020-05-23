package mobdao.com.openquiz.models

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Question(
    val category: Category,
    val type: QuestionType,
    val difficulty: Difficulty,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
) : Parcelable {

    @IgnoredOnParcel
    var answeredOption: String? = null
        private set

    fun answer(option: String?): Boolean {
        if (option != null && option != correctAnswer && !incorrectAnswers.contains(option))
            throw IllegalArgumentException("Not a valid option")

        answeredOption = option

        return correctAnswer == answeredOption
    }

    fun getOptions(): List<String> =
        (listOf(correctAnswer) + incorrectAnswers).shuffled(Random(System.currentTimeMillis()))
}