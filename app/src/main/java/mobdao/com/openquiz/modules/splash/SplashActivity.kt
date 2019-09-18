package mobdao.com.openquiz.modules.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import mobdao.com.openquiz.modules.main.MainActivity

class SplashActivity : AppCompatActivity() {

    //region Lifecycle

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        startMainModule()
    }

    //endregion

    //region private

    private fun startMainModule() {
        Handler().postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 2500)
    }

    //endregion

}