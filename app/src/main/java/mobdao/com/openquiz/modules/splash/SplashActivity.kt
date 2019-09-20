package mobdao.com.openquiz.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mobdao.com.openquiz.modules.main.MainActivity
import mobdao.com.openquiz.utils.constants.IntentConstants.INITIAL_SCREEN
import mobdao.com.openquiz.utils.enums.InitialScreenType.HOME
import mobdao.com.openquiz.utils.enums.InitialScreenType.LOGIN
import mobdao.com.openquiz.utils.extensions.getViewModel
import mobdao.com.openquiz.utils.extensions.setupObserver

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by lazy(::getViewModel)

    //region Lifecycle

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    //endregion

    //region private

    private fun setupObservers() = with(viewModel) {
        setupObserver(isUserLoggedInLiveData to { isUserLoggedIn ->
            val initialScreenType = if (isUserLoggedIn) HOME else LOGIN
            Intent(this@SplashActivity, MainActivity::class.java).apply {
                putExtra(INITIAL_SCREEN, initialScreenType)
                startActivity(this)
                finish()
            }
        })
    }

    //endregion

}