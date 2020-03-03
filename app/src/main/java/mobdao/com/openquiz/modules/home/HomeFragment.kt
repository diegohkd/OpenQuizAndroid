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
import mobdao.com.openquiz.data.di.components.DaggerDataComponent
import mobdao.com.openquiz.di.components.DaggerHomeComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: HomeViewModel by viewModels { viewModelFactory }

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
        setupInjections()
        setupView()
        setupObservers()
    }

    //endregion

    //region private

    private fun setupInjections() {
        DaggerHomeComponent
            .builder()
            .dataComponent(DaggerDataComponent.create())
            .build()
            .inject(this)
    }

    private fun setupView() {
        startQuizButton.setOnClickListener {
            viewModel.onStartQuizClicked()
        }

        signOutButton.setOnClickListener {
            viewModel.onSignOutClicked()
        }
    }

    private fun setupObservers() = with(viewModel) {
        setupProgressBarObserver(progressBar)
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