package mobdao.com.openquiz.modules.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_login.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.di.components.DaggerLoginComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.constants.RequestCodeConstants.RC_SIGN_IN
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerLoginComponent.create().inject(this)
        setupView()
        setupObservers()
    }

    //endregion

    //region Fragment

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    //endregion

    //region private

    private fun setupObservers() = with(viewModel) {
        setupSingleEventObserver(showHomeScreenEvent to {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        })
        setupSingleEventObserver(errorEvent to {
            showDefaultErrorDialog()
        })
    }

    private fun setupView() {
        googleSignInButton.setOnClickListener {
            showGoogleSignIn()
        }
    }

    private fun showGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //endregion
}