package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import mobdao.com.openquiz.databinding.FragmentQuestionBinding
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.QuizFragment.Companion.scopeId
import mobdao.com.openquiz.modules.quiz.QuizFragment.Companion.scopeName
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.extensions.injectOrNull
import mobdao.com.openquiz.utils.extensions.safeGetParcelable
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.qualifier.named

open class QuestionFragment : BaseFragment() {

    private lateinit var binding: FragmentQuestionBinding

    private val scope = getKoin().getOrCreateScope(scopeId, named(scopeName))
    private val viewModelScopeOwner: ViewModelStoreOwner? by injectOrNull(scope)

    @Suppress("RemoveExplicitTypeArguments")
    override val viewModel: QuizViewModel by lazy {
        scope.getViewModel<QuizViewModel>(viewModelScopeOwner ?: requireParentFragment())
    }

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