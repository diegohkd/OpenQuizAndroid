package mobdao.com.openquiz.modules.quiz.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobdao.com.openquiz.databinding.FragmentQuestionBinding
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import javax.inject.Inject

open class QuestionFragment : BaseFragment() {

    private lateinit var binding: FragmentQuestionBinding

    @Inject
    override lateinit var viewModel: QuizViewModel

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentQuestionBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        childFragmentManager
        binding.viewModel = viewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onResume() {
        super.onResume()
        binding.question?.let(viewModel::onResume)
    }

    //endregion

    //region private

    private fun injectDependencies() {
        (requireParentFragment() as QuizFragment).quizComponent.inject(this)
    }

    private fun handleArguments() {
        arguments?.safeGetParcelable<Question>(QUESTION)?.let(::bindQuestion)
    }

    private fun bindQuestion(question: Question) = with(question) {
        binding.question = this
        binding.fragmentManager = childFragmentManager
    }

    //endregion
}
