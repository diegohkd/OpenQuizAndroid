package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mobdao.com.openquiz.databinding.FragmentQuestionBinding
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.safeGetParcelable

@AndroidEntryPoint
class QuestionFragment : BaseFragment() {

    private lateinit var binding: FragmentQuestionBinding

    override val viewModel: QuizViewModel by activityViewModels()

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentQuestionBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        binding.question?.let(viewModel::onResume)
    }

    //endregion

    //region private

    private fun handleArguments() {
        arguments?.safeGetParcelable<Question>(QUESTION)?.let(::bindQuestion)
    }

    private fun bindQuestion(question: Question) = with(question) {
        binding.question = this
        binding.fragmentManager = childFragmentManager
    }

    //endregion
}
