package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.text.HtmlCompat
import mobdao.com.openquiz.models.Question

@Suppress("UNUSED_PARAMETER")
abstract class BaseQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    protected abstract var question: Question?

    private val mutableAnswers = mutableListOf<String>()
    protected val answers: List<String> = mutableAnswers
        get() {
            if (mutableAnswers.isEmpty()) {
                question?.correctAnswer?.let { mutableAnswers.add(it) }
                mutableAnswers.addAll(question?.incorrectAnswers.orEmpty().shuffled())
            }
            return field
        }

    abstract fun bind(question: Question, answerCallback: (String) -> Unit)

    protected fun getQuestionText(): Spanned =
        HtmlCompat.fromHtml(
            question?.question.orEmpty(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
}