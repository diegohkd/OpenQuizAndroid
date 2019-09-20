package mobdao.com.openquiz.modules.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            // TODO route to correct screen
            if (isUserLoggedIn) {
                Toast.makeText(this@SplashActivity, "is logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SplashActivity, "is not logged in", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //endregion

}