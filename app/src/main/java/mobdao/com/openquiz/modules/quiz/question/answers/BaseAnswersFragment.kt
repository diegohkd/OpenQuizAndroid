package mobdao.com.openquiz.modules.quiz.question.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver

@AndroidEntryPoint
abstract class BaseAnswersFragment : BaseFragment() {

    protected abstract val layout: Int
    protected lateinit var question: Question

    override val viewModel: QuizViewModel by activityViewModels()

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
            setupObserver(
                showCorrectAnswerEvent to { _ ->
                    showCorrectAnswer()
                }
            )
        }
        getShowUnansweredQuestionEvent(question)?.let { showUnansweredQuestionEvent ->
            setupObserver(
                showUnansweredQuestionEvent to { _ ->
                    showUnansweredQuestion()
                }
            )
        }
        getConfirmAnswerEvent(question)?.let { confirmAnswerEvent ->
            setupSingleEventObserver(
                confirmAnswerEvent to {
                    viewModel.onConfirmAnswer(question, getSelectedAnswer())
                }
            )
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
