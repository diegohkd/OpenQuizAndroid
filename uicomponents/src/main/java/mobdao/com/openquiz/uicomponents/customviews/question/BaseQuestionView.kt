package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import mobdao.com.openquiz.models.Question

@Suppress("UNUSED_PARAMETER")
abstract class BaseQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    abstract var question: Question?

    private val mutableAnswers = mutableListOf<String>()
    protected val answers: List<String> = mutableAnswers
        get() {
            if (mutableAnswers.isEmpty()) {
                question?.correctAnswer?.let { mutableAnswers.add(it) }
                mutableAnswers.addAll(question?.incorrectAnswers.orEmpty())
                mutableAnswers.shuffle()
            }
            return field
        }

    abstract fun bind(question: Question, answerSelectedCallback: () -> Unit)
    abstract fun getSelectedAnswer(): String
    abstract fun showCorrectAnswer()
}