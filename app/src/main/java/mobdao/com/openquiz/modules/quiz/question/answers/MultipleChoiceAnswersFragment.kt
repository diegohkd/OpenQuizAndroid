package mobdao.com.openquiz.modules.quiz.question.answers

import android.graphics.Color
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.core.view.updateMargins
import kotlinx.android.synthetic.main.fragment_multiple_choice_question.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.utils.extensions.fromHtml
import mobdao.com.openquiz.utils.extensions.indexOfFirstOrNull
import mobdao.com.openquiz.utils.helpers.DimensionHelper

class MultipleChoiceAnswersFragment : BaseAnswersFragment() {

    override val layout: Int = R.layout.fragment_multiple_choice_question

    private val answersOptions: List<String> by lazy { question.getOptions() }
    private val selectedRadioButton: RadioButton?
        get() = if (radioGroup.checkedRadioButtonId != -1) {
            view?.findViewById(radioGroup.checkedRadioButtonId)
        } else {
            null
        }

    override fun bind(question: Question) {
        this.question = question

        bindAnswersOptions()

        addAnswerSelectionListener()
    }

    override fun getSelectedAnswer(): String {
        return answersOptions.getOrNull(selectedAnswerIndex()).orEmpty()
    }

    override fun showCorrectAnswer() {
        radioGroup.children.forEach {
            it.isClickable = false
        }

        selectedRadioButton?.setTextColor(Color.RED)

        val correctAnswerIndex = answersOptions.indexOfFirstOrNull {
            it == question.correctAnswer
        } ?: return
        val correctRadioButton = radioGroup.getChildAt(correctAnswerIndex) as? RadioButton ?: return
        correctRadioButton.setTextColor(Color.GREEN)
    }

    //region private

    private fun bindAnswersOptions() {
        answersOptions.forEach { answer ->
            addRadioButtonForAnswerOption(answer)
        }
    }

    private fun addRadioButtonForAnswerOption(option: String) {
        val radioButton = RadioButton(context)
        radioButton.layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.MATCH_PARENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            updateMargins(
                top = DimensionHelper.convertDpToPixel(
                    16f,
                    requireContext()
                ).toInt()
            )
        }
        radioButton.text = option.fromHtml()
        radioGroup.addView(radioButton)
    }

    private fun addAnswerSelectionListener() {
        radioGroup.setOnCheckedChangeListener { _, _ ->
            viewModel.onAnswerClicked(question)
        }
    }

    private fun selectedAnswerIndex(): Int {
        val selectedRadioButton = view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        return radioGroup.indexOfChild(selectedRadioButton)
    }

    //endregion
}