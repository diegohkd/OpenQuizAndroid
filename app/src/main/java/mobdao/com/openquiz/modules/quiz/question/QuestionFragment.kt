package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_question.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.di.components.DaggerQuestionComponent
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType.MULTIPLE_CHOICE
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.uicomponents.customviews.question.MultipleChoiceQuestionView
import mobdao.com.openquiz.uicomponents.customviews.question.TrueFalseQuestionView
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.sharedViewModel
import javax.inject.Inject

class QuestionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: QuizViewModel by sharedViewModel { viewModelFactory }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjections()
        handleArguments()
        setupView()
    }

    //endregion

    //region private

    private fun setupInjections() {
        DaggerQuestionComponent
            .create()
            .inject(this)
    }

    private fun handleArguments() {
        arguments?.takeIf { it.containsKey(QUESTION) }?.getParcelable<Question>(QUESTION)?.apply {
            val questionView =
                if (type == MULTIPLE_CHOICE) MultipleChoiceQuestionView(requireContext())
                else TrueFalseQuestionView(requireContext())
            questionView.bind(this) {
                confirmButton.isEnabled = true
            }

            questionContainer.addView(questionView)
        }
    }

    private fun setupView() {
        confirmButton.setOnClickListener {
            viewModel.onConfirmClicked()
        }
    }

    //endregion
}