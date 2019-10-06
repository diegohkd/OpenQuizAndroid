package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.view_true_false_question.view.*
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.uicomponents.R

@Suppress("UNUSED_PARAMETER")
class TrueFalseQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseQuestionView(context, attrs, defStyleAttr) {

    override var question: Question? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_true_false_question, this, true)
    }

    override fun bind(question: Question, answerCallback: (String) -> Unit) {
        this.question = question

        questionTextView.text = getQuestionText()
        setupToggleButton(falseButton, trueButton, answerCallback)
        setupToggleButton(trueButton, falseButton, answerCallback)
    }

    //region private

    private fun setupToggleButton(
        button: ToggleButton,
        otherToggleButton: ToggleButton,
        answerCallback: (String) -> Unit
    ) {
        button.setOnClickListener {
            answerCallback(button.textOn.toString())
            if (button.isChecked) {
                otherToggleButton.isChecked = false
            } else {
                button.isChecked = true
            }
        }
    }

    //endregion
}