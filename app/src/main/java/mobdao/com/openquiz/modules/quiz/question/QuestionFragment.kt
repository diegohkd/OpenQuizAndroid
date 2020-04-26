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
import mobdao.com.openquiz.modules.quiz.question.answers.MultipleChoiceAnswersFragment
import mobdao.com.openquiz.modules.quiz.question.answers.TrueFalseAnswersFragment
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.*
import javax.inject.Inject

open class QuestionFragment : BaseFragment() {

    @Inject
    open lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: QuizViewModel by sharedViewModel { viewModelFactory }
    lateinit var question: Question

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjections()
        handleArguments()
        setupView()
        setupObservers()
    }

    //endregion

    //region private

    private fun setupInjections() {
        DaggerQuestionComponent
            .create()
            .inject(this)
    }

    private fun handleArguments() {
        arguments?.takeIf { it.containsKey(QUESTION) }?.getParcelable<Question>(QUESTION)?.let {
            bindQuestion(it)
        }
    }

    private fun setupView() {
        confirmButton.setOnClickListener {
            viewModel.onConfirmAnswerClicked(question)
        }
        nextButton.setOnClickListener {
            viewModel.onNextClicked()
        }
    }

    private fun setupObservers() = with(viewModel) {
        getSelectedAnswerEvent(question)?.let { showCorrectAnswerEvent ->
            setupSingleEventObserver(showCorrectAnswerEvent to {
                confirmButton.isEnabled = true
            })
        }
        getShowCorrectAnswerEvent(question)?.let { showCorrectAnswerEvent ->
            setupSingleEventObserver(showCorrectAnswerEvent to {
                confirmButton.gone()
                nextButton.visible()
            })
        }
    }

    private fun bindQuestion(question: Question) = with(question) {
        this@QuestionFragment.question = question
        questionTextView.text = question.question.fromHtml()
        bindAnswersFragment(question)
    }

    private fun bindAnswersFragment(question: Question) {
        val fragment = if (question.type == MULTIPLE_CHOICE) {
            MultipleChoiceAnswersFragment()
        } else {
            TrueFalseAnswersFragment()
        }
        fragment.arguments = Bundle().apply { putParcelable(QUESTION, question) }
        childFragmentManager.showFragmentOnContainer(R.id.answersContainer, fragment)
    }

    //endregion

}