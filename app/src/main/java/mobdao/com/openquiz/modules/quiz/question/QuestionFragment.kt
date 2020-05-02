package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobdao.com.openquiz.databinding.FragmentQuestionBinding
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class QuestionFragment : BaseFragment() {

    private lateinit var binding: FragmentQuestionBinding
    override val viewModel: QuizViewModel by sharedViewModel()

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
        binding.viewModel = viewModel
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