package mobdao.com.openquiz.modules.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import mobdao.com.openquiz.R
import mobdao.com.openquiz.modules.login.LoginViewModel
import mobdao.com.openquiz.utils.constants.IntentConstants.INITIAL_SCREEN
import mobdao.com.openquiz.utils.enums.InitialScreenType
import mobdao.com.openquiz.utils.enums.InitialScreenType.HOME

class MainActivity : AppCompatActivity() {

    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val initialScreenType = intent.extras?.getSerializable(INITIAL_SCREEN) as? InitialScreenType
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id != R.id.mainFragment) {
            return
        }

        if (initialScreenType == HOME) {
            navController.navigate(R.id.to_homeFragment)
        } else {
            navController.navigate(R.id.to_loginFragment)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
