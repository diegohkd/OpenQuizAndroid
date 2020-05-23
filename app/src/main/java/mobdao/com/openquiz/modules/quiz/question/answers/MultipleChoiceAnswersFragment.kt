package mobdao.com.openquiz.modules.quiz.question.answers

import android.graphics.Color
import android.view.View
import android.widget.RadioButton
import androidx.core.view.children
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentMultipleChoiceQuestionBinding
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.utils.extensions.getRadioButtonAt
import mobdao.com.openquiz.utils.extensions.indexOfFirstOrNull
import mobdao.com.openquiz.utils.extensions.selectedRadioButton

class MultipleChoiceAnswersFragment : BaseAnswersFragment() {

    override val layout: Int = R.layout.fragment_multiple_choice_question

    private lateinit var binding: FragmentMultipleChoiceQuestionBinding
    private val answersOptions: List<String> by lazy { question.getOptions() }

    override fun onCreateView(): View? =
        FragmentMultipleChoiceQuestionBinding.inflate(layoutInflater).apply {
            binding = this
        }.root

    override fun bind(question: Question) {
        this.question = question
        binding.question = question
        binding.viewModel = viewModel
        binding.answersOptions = answersOptions
    }

    override fun getSelectedAnswer(): String {
        return answersOptions.getOrNull(selectedAnswerIndex()).orEmpty()
    }

    override fun showCorrectAnswer(): Unit = with(binding.radioGroup) {
        children.forEach { it.isClickable = false }

        selectedRadioButton?.setTextColor(Color.RED)

        getCorrectRadioButton()?.setTextColor(Color.GREEN)
    }

    override fun showUnansweredQuestion(): Unit = with(binding.radioGroup) {
        selectedRadioButton?.isChecked = false
        children.forEach {
            it.isClickable = false
            (it as? RadioButton)?.setTextColor(Color.RED)
        }
        getCorrectRadioButton()?.setTextColor(Color.GREEN)
    }

    //region private

    private fun selectedAnswerIndex(): Int = with(binding.radioGroup) {
        indexOfChild(selectedRadioButton)
    }

    private fun getCorrectRadioButton(): RadioButton? {
        val correctAnswerIndex = answersOptions.indexOfFirstOrNull {
            it == question.correctAnswer
        } ?: return null
        return binding.radioGroup.getRadioButtonAt(correctAnswerIndex)
    }

    //endregion
}