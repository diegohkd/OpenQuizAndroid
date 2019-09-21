package mobdao.com.openquiz.modules.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import mobdao.com.openquiz.R
import mobdao.com.openquiz.utils.constants.IntentConstants.INITIAL_SCREEN
import mobdao.com.openquiz.utils.enums.InitialScreenType
import mobdao.com.openquiz.utils.enums.InitialScreenType.HOME

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val initialScreenType = intent.extras?.getSerializable(INITIAL_SCREEN) as? InitialScreenType

        if (initialScreenType == HOME) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_homeFragment)
        } else {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
