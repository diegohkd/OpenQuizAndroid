package mobdao.com.openquiz.uicomponents.customviews.question

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.updateMargins
import kotlinx.android.synthetic.main.view_multiple_choice_question.view.*
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.uicomponents.R
import mobdao.com.openquiz.utils.helpers.DimensionHelper

@Suppress("UNUSED_PARAMETER")
class MultipleChoiceQuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseQuestionView(context, attrs, defStyleAttr) {

    override var question: Question? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_multiple_choice_question, this, true)
    }

    override fun bind(question: Question, answerCallback: (String) -> Unit) {
        this.question = question

        questionTextView.text = getQuestionText()

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
            radioButton.text = answer
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener { _, id ->
            findViewById<RadioButton>(id)?.text?.toString()?.let { answerCallback(it) }
        }
    }
}