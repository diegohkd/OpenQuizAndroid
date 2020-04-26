package mobdao.com.openquiz.modules.quiz.question.answers

import android.graphics.Color
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.fragment_true_false_question.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.models.Question

class TrueFalseAnswersFragment : BaseAnswersFragment() {

    override val layout: Int = R.layout.fragment_true_false_question

    private val selectedButton: ToggleButton?
        get() = when {
            falseButton.isChecked -> falseButton
            trueButton.isChecked -> trueButton
            else -> null
        }

    override fun bind(question: Question) {
        this.question = question

        setupToggleButton(falseButton, trueButton)
        setupToggleButton(trueButton, falseButton)
    }

    override fun getSelectedAnswer(): String {
        return if (falseButton.isChecked) falseButton.textOff.toString()
        else trueButton.textOff.toString()
    }

    override fun showCorrectAnswer() {
        selectedButton?.setTextColor(Color.RED)

        val correctButton =
            if (falseButton.textOff == question.correctAnswer) falseButton else trueButton
        correctButton?.setTextColor(Color.GREEN)
    }

    // region private

    private fun setupToggleButton(
        button: ToggleButton,
        otherToggleButton: ToggleButton
    ) {
        button.setOnClickListener {
            if (button.isChecked) otherToggleButton.isChecked = false
            else button.isChecked = true
            viewModel.onAnswerClicked(question)
        }
    }

    // endregion
}