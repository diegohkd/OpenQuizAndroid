package mobdao.com.openquiz.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentHomeBinding
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    override val viewModel: HomeViewModel by viewModel()

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeBinding.inflate(layoutInflater).apply {
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

        setupSingleEventObserver(signOutEvent to {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        })

        setupObserver(startQuizEvent to { questions ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToQuizFragment(questions.toTypedArray())
            findNavController().navigate(action)
        })
    }

    //endregion
}