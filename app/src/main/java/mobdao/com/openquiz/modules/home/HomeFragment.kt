package mobdao.com.openquiz.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobdao.com.openquiz.OpenQuizApplication
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentHomeBinding
import mobdao.com.openquiz.di.components.HomeComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeComponent: HomeComponent

    @Inject
    override lateinit var viewModel: HomeViewModel

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressed {
            showAlertDialog(
                title = getString(R.string.exit_the_app),
                positiveButtonText = R.string.yes,
                negativeButtonText = R.string.no,
                positiveCallback = { requireActivity().finish() }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        binding.viewmodel = viewModel
        setupObservers()
    }

    //endregion

    //region private

    private fun injectDependencies() {
        homeComponent = (requireActivity().applicationContext as OpenQuizApplication)
            .appComponent
            .homeComponent()
            .create()

        homeComponent.inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        setupGenericErrorObserver()
        setupNavigationObserver()
    }

    //endregion
}
