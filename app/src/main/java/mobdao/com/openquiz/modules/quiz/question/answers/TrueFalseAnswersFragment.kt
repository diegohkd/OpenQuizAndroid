package mobdao.com.openquiz.modules.quiz.question.answers

import android.graphics.Color
import android.view.View
import android.widget.ToggleButton
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentTrueFalseQuestionBinding
import mobdao.com.openquiz.models.Question

class TrueFalseAnswersFragment : BaseAnswersFragment() {

    override val layout: Int = R.layout.fragment_true_false_question

    private lateinit var binding: FragmentTrueFalseQuestionBinding
    private val falseButton: ToggleButton get() = binding.falseButton
    private val trueButton: ToggleButton get() = binding.trueButton
    private val selectedButton: ToggleButton?
        get() = when {
            falseButton.isChecked -> falseButton
            trueButton.isChecked -> trueButton
            else -> null
        }

    override fun onCreateView(): View? =
        FragmentTrueFalseQuestionBinding.inflate(layoutInflater).apply {
            binding = this
        }.root

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

        getCorrectButton().setTextColor(Color.GREEN)
    }

    override fun showUnansweredQuestion() {
        selectedButton?.isChecked = false
        falseButton.setTextColor(Color.RED)
        falseButton.isClickable = false
        trueButton.setTextColor(Color.RED)
        trueButton.isClickable = false
        getCorrectButton().setTextColor(Color.GREEN)
    }

    // region private

    private fun setupToggleButton(button: ToggleButton, otherToggleButton: ToggleButton) {
        button.setOnClickListener {
            if (button.isChecked) otherToggleButton.isChecked = false
            else button.isChecked = true
            viewModel.onAnswerClicked(question)
        }
    }

    private fun getCorrectButton(): ToggleButton =
        if (falseButton.textOff == question.correctAnswer) falseButton
        else trueButton

    // endregion
}