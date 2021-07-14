package mobdao.com.openquiz.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentHomeBinding
import mobdao.com.openquiz.modules.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override val viewModel: HomeViewModel by viewModels()

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
        binding.viewmodel = viewModel
        setupObservers()
    }

    //endregion

    //region private

    private fun setupObservers() = with(viewModel) {
        setupGenericErrorObserver()
        setupNavigationObserver()
    }

    //endregion
}
