package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.view_true_false_question.view.*
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.uicomponents.R
import mobdao.com.openquiz.utils.extensions.fromHtml

@Suppress("UNUSED_PARAMETER")
class TrueFalseQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseQuestionView(context, attrs, defStyleAttr) {

    override var question: Question? = null
    private val selectedButton: ToggleButton?
        get() = when {
            falseButton.isChecked -> falseButton
            trueButton.isChecked -> trueButton
            else -> null
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_true_false_question, this, true)
    }

    override fun bind(question: Question, answerSelectedCallback: () -> Unit) {
        this.question = question

        questionTextView.text = question.question.fromHtml()
        setupToggleButton(falseButton, trueButton, answerSelectedCallback)
        setupToggleButton(trueButton, falseButton, answerSelectedCallback)
    }

    override fun getSelectedAnswer(): String =
        if (falseButton.isChecked) falseButton.textOff.toString()
        else trueButton.textOff.toString()

    override fun showCorrectAnswer() {
        selectedButton?.setTextColor(RED)

        val correctButton =
            if (falseButton.textOff == question?.correctAnswer) falseButton else trueButton
        correctButton?.setTextColor(GREEN)
    }

    //region private

    private fun setupToggleButton(
        button: ToggleButton,
        otherToggleButton: ToggleButton,
        answerSelected: () -> Unit
    ) {
        button.setOnClickListener {
            if (button.isChecked) otherToggleButton.isChecked = false
            else button.isChecked = true
            answerSelected()
        }
    }

    //endregion
}