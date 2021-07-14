package mobdao.com.openquiz.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mobdao.com.openquiz.OpenQuizApplication
import mobdao.com.openquiz.di.components.SplashComponent
import mobdao.com.openquiz.modules.main.MainActivity
import mobdao.com.openquiz.utils.constants.IntentConstants.INITIAL_SCREEN
import mobdao.com.openquiz.utils.enums.InitialScreenType
import mobdao.com.openquiz.utils.enums.InitialScreenType.HOME
import mobdao.com.openquiz.utils.enums.InitialScreenType.LOGIN
import mobdao.com.openquiz.utils.extensions.setupObserver
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private lateinit var splashComponent: SplashComponent

    @Inject
    lateinit var viewModel: SplashViewModel

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setupObservers()
    }

    //endregion

    //region private

    private fun injectDependencies() {
        splashComponent = (applicationContext as OpenQuizApplication)
            .appComponent
            .splashComponent()
            .create()

        splashComponent.inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        setupObserver(
            isUserLoggedInLiveData to { isUserLoggedIn ->
                val initialScreenType = if (isUserLoggedIn) HOME else LOGIN
                showMain(initialScreenType)
            }
        )
    }

    private fun showMain(initialScreenType: InitialScreenType) {
        Intent(this@SplashActivity, MainActivity::class.java).apply {
            putExtra(INITIAL_SCREEN, initialScreenType)
            startActivity(this)
            finish()
        }
    }

    //endregion
}
