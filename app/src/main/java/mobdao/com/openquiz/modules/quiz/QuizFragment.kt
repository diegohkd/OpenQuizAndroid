package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mobdao.com.openquiz.OpenQuizApplication
import mobdao.com.openquiz.databinding.FragmentQuizBinding
import mobdao.com.openquiz.di.components.QuizComponent
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.factories.FragmentFactory
import javax.inject.Inject

class QuizFragment : BaseFragment() {

    lateinit var quizComponent: QuizComponent

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    override lateinit var viewModel: QuizViewModel

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
        injectDependencies()
        setupObservers()
        initViewModel()
        binding.viewModel = viewModel
        binding.fragmentFactory = fragmentFactory
    }

    //endregion

    //region private

    private fun injectDependencies() {
        quizComponent = (requireActivity().applicationContext as OpenQuizApplication)
            .appComponent
            .quizComponent()
            .create()
        quizComponent.inject(this)
    }

    private fun setupObservers() {
        setupNavigationObserver()
    }

    private fun initViewModel() {
        val questions = args.questions.toList()
        viewModel.init(Game(questions), questions)
    }

    //endregion
}
