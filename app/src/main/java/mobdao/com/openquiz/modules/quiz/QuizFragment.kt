package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mobdao.com.openquiz.databinding.FragmentQuizBinding
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.factories.FragmentFactory
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.qualifier.named

class QuizFragment : BaseFragment() {

    private val fragmentFactory: FragmentFactory by inject()
    private val args: QuizFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizBinding

    private val scope = getKoin().getOrCreateScope(scopeId, named(scopeName))

    @Suppress("RemoveExplicitTypeArguments")
    override val viewModel: QuizViewModel by lazy {
        scope.getViewModel<QuizViewModel>(this)
    }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentQuizBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragmentManager = childFragmentManager
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initViewModel()
        binding.viewModel = viewModel
        binding.fragmentFactory = fragmentFactory
    }

    //endregion

    //region private

    private fun setupObservers() {
        setupNavigationObserver()
    }

    private fun initViewModel() {
        val questions = args.questions.toList()
        viewModel.init(Game(questions), questions)
    }

    //endregion

    companion object {
        internal const val scopeId = "QuizFragment"
        internal const val scopeName = "QuizFragment"
    }
}
