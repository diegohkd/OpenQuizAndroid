package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mobdao.com.openquiz.databinding.FragmentQuizBinding
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.factories.FragmentFactory
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : BaseFragment() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override val viewModel: QuizViewModel by activityViewModels()

    private val args: QuizFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizBinding

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentQuizBinding.inflate(layoutInflater).apply {
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
}
