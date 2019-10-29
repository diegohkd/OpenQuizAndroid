package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.core.view.updateMargins
import kotlinx.android.synthetic.main.view_multiple_choice_question.view.*
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.uicomponents.R
import mobdao.com.openquiz.utils.extensions.fromHtml
import mobdao.com.openquiz.utils.extensions.indexOfFirstOrNull
import mobdao.com.openquiz.utils.helpers.DimensionHelper

@Suppress("UNUSED_PARAMETER")
class MultipleChoiceQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseQuestionView(context, attrs, defStyleAttr) {

    override var question: Question? = null
    private val selectedRadioButton: RadioButton?
        get() =
            if (radioGroup.checkedRadioButtonId != -1)
                findViewById(radioGroup.checkedRadioButtonId)
            else
                null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_multiple_choice_question, this, true)
    }

    override fun bind(question: Question, answerSelectedCallback: () -> Unit) {
        this.question = question

        questionTextView.text = question.question.orEmpty().fromHtml()

        answers.forEach { answer ->
            val radioButton = RadioButton(context)
            radioButton.layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                updateMargins(
                    top = DimensionHelper.convertDpToPixel(
                        16f,
                        context
                    ).toInt()
                )
            }
            radioButton.text = answer.fromHtml()
            radioGroup.addView(radioButton)
        }

        handleAnswerSelection(answerSelectedCallback)
    }

    override fun getSelectedAnswer(): String =
        answers.getOrNull(selectedAnswerIndex()).orEmpty()

    override fun showCorrectAnswer() {
        radioGroup.children.forEach {
            it.isClickable = false
        }

        selectedRadioButton?.setTextColor(RED)

        val correctAnswerIndex =
            answers.indexOfFirstOrNull { it == question?.correctAnswer } ?: return
        val correctRadioButton = radioGroup.getChildAt(correctAnswerIndex) as? RadioButton ?: return
        correctRadioButton.setTextColor(GREEN)
    }

    //region private

    private fun handleAnswerSelection(answerSelectedCallback: () -> Unit) {
        radioGroup.setOnCheckedChangeListener { _, _ ->
            answerSelectedCallback()
        }
    }

    private fun selectedAnswerIndex(): Int {
        val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        return radioGroup.indexOfChild(selectedRadioButton)
    }

    //endregion
}