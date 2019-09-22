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

    lateinit var viewModelFactory: ViewModelProvider.Factory
        @Inject set

    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    //endregion
}