package mobdao.com.openquiz.modules.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mobdao.com.openquiz.data.di.components.DaggerDataComponent
import mobdao.com.openquiz.di.components.DaggerSplashComponent
import mobdao.com.openquiz.modules.main.MainActivity
import mobdao.com.openquiz.utils.constants.IntentConstants.INITIAL_SCREEN
import mobdao.com.openquiz.utils.enums.InitialScreenType
import mobdao.com.openquiz.utils.enums.InitialScreenType.HOME
import mobdao.com.openquiz.utils.enums.InitialScreenType.LOGIN
import mobdao.com.openquiz.utils.extensions.setupObserver
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by viewModels { viewModelFactory }

    //region Lifecycle

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        DaggerSplashComponent
            .builder()
            .dataComponent(DaggerDataComponent.create())
            .build()
            .inject(this)
        setupObservers()
    }

    //endregion

    //region private

    private fun setupObservers() = with(viewModel) {
        setupObserver(isUserLoggedInLiveData to { isUserLoggedIn ->
            val initialScreenType = if (isUserLoggedIn) HOME else LOGIN
            showMain(initialScreenType)
        })
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