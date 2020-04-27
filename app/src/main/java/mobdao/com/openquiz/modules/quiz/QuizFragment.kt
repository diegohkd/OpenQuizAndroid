package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mobdao.com.openquiz.databinding.FragmentQuizBinding
import mobdao.com.openquiz.di.components.DaggerQuizComponent
import mobdao.com.openquiz.models.Game
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.sharedViewModel
import mobdao.com.openquiz.utils.factories.FragmentFactory
import javax.inject.Inject

class QuizFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    private val args: QuizFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizBinding
    override val viewModel: QuizViewModel by sharedViewModel { viewModelFactory }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentQuizBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragmentManager = parentFragmentManager
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjections()
        setupObservers()
        initViewModel()
        binding.viewModel = viewModel
        binding.fragmentFactory = fragmentFactory
    }

    //endregion

    //region private

    private fun setupInjections() {
        DaggerQuizComponent
            .create()
            .inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        setupObserver(showResultsReportEvent to { resultsReport ->
            val action =
                QuizFragmentDirections.actionQuizFragmentToResultsReportFragment(resultsReport)
            findNavController().navigate(action)
        })
    }

    private fun initViewModel() {
        val questions = args.questions.toList()
        viewModel.init(Game(questions), questions)
    }

    //endregion
}