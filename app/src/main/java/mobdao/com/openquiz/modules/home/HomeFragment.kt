package mobdao.com.openquiz.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.di.components.DaggerHomeComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerHomeComponent.create().inject(this)
        setupView()
        setupObservers()
    }

    //endregion

    //region private

    private fun setupObservers() = with(viewModel) {
        setupSingleEventObserver(signOutEvent to {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        })
    }

    private fun setupView() {
        signOutButton.setOnClickListener {
            viewModel.onSignOutClicked()
        }
    }

    //endregion
}