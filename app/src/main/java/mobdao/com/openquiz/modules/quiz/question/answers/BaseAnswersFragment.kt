package mobdao.com.openquiz.modules.quiz.question.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizFragment.Companion.scopeId
import mobdao.com.openquiz.modules.quiz.QuizFragment.Companion.scopeName
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.injectOrNull
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.qualifier.named

abstract class BaseAnswersFragment : BaseFragment() {

    protected abstract val layout: Int
    protected lateinit var question: Question

    private val scope = getKoin().getOrCreateScope(scopeId, named(scopeName))
    private val viewModelScopeOwner: ViewModelStoreOwner? by injectOrNull(scope)

    @Suppress("RemoveExplicitTypeArguments")
    override val viewModel: QuizViewModel by lazy {
        scope.getViewModel<QuizViewModel>(
            viewModelScopeOwner ?: requireParentFragment().requireParentFragment()
        )
    }

    // region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        setupObservers()
    }

    // endregion

    // region private

    private fun handleArguments() {
        arguments?.safeGetParcelable<Question>(QUESTION)?.let(::bind)
    }

    @Suppress("RedundantLambdaArrow")
    private fun setupObservers() = with(viewModel) {
        getShowCorrectAnswerEvent(question)?.let { showCorrectAnswerEvent ->
            setupObserver(showCorrectAnswerEvent to { _ ->
                showCorrectAnswer()
            })
        }
        getShowUnansweredQuestionEvent(question)?.let { showUnansweredQuestionEvent ->
            setupObserver(showUnansweredQuestionEvent to { _ ->
                showUnansweredQuestion()
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
    protected abstract fun showUnansweredQuestion()

    // endregion
}