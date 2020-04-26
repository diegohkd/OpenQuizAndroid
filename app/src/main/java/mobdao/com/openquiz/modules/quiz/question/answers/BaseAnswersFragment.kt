package mobdao.com.openquiz.modules.quiz.question.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import mobdao.com.openquiz.di.components.DaggerQuestionComponent
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import mobdao.com.openquiz.utils.extensions.sharedViewModel
import javax.inject.Inject

abstract class BaseAnswersFragment : BaseFragment() {

    @Inject
    open lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var question: Question
    protected abstract val layout: Int

    override val viewModel: QuizViewModel by sharedViewModel { viewModelFactory }

    // region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjections()
        handleArguments()
        setupObservers()
    }

    // endregion

    // region private

    private fun setupInjections() {
        DaggerQuestionComponent
            .create()
            .inject(this)
    }

    private fun handleArguments() {
        arguments?.safeGetParcelable<Question>(QUESTION)?.let(::bind)
    }

    private fun setupObservers() = with(viewModel) {
        getShowCorrectAnswerEvent(question)?.let { showCorrectAnswerEvent ->
            setupObserver(showCorrectAnswerEvent to { _ ->
                showCorrectAnswer()
            })
        }
        getConfirmAnswerEvent(question)?.let { confirmAnswerEvent ->
            setupSingleEventObserver(confirmAnswerEvent to {
                viewModel.onConfirmAnswer(question, getSelectedAnswer())
            })
        }
    }

    // endregion

    // region private

    protected abstract fun onCreateView(): View?
    protected abstract fun bind(question: Question)
    protected abstract fun getSelectedAnswer(): String
    protected abstract fun showCorrectAnswer()

    // endregion
}