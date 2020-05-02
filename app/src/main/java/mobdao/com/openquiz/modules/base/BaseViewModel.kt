package mobdao.com.openquiz.modules.base

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import mobdao.com.openquiz.utils.livedata.LiveEvent

abstract class BaseViewModel : ViewModel() {

    val progressBarVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply { value = GONE }
    }

    @Suppress("RemoveExplicitTypeArguments")
    val genericErrorEvent: LiveEvent<Unit> by lazy { LiveEvent<Unit>() }

    val routeEvent = LiveEvent<NavDirections>()

    protected fun showProgressBar() = progressBarVisibility.postValue(VISIBLE)
    protected fun hideProgressBar() = progressBarVisibility.postValue(GONE)
}