package mobdao.com.openquiz.modules.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import mobdao.com.openquiz.OpenQuizApplication
import mobdao.com.openquiz.R
import mobdao.com.openquiz.databinding.FragmentLoginBinding
import mobdao.com.openquiz.di.components.LoginComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.utils.constants.RequestCodeConstants.RC_SIGN_IN
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginComponent: LoginComponent

    @Inject
    override lateinit var viewModel: LoginViewModel

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(layoutInflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        setupObservers()
        binding.viewmodel = viewModel
    }

    //endregion

    //region Fragment

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    //endregion

    //region private

    private fun injectDependencies() {
        loginComponent = (requireActivity().applicationContext as OpenQuizApplication)
            .appComponent
            .loginComponent()
            .create()

        loginComponent.inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        setupGenericErrorObserver()
        setupNavigationObserver()
        setupSingleEventObserver(
            showGoogleSignInEvent to {
                showGoogleSignIn()
            }
        )
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
