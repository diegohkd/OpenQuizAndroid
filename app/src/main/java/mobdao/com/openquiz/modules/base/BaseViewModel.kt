package mobdao.com.openquiz.modules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobdao.com.openquiz.utils.livedata.LiveEvent

abstract class BaseViewModel : ViewModel() {
    val showProgressBarEvent: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    @Suppress("RemoveExplicitTypeArguments")
    val genericErrorEvent: LiveEvent<Unit> by lazy { LiveEvent<Unit>() }

    protected fun showProgressBar() = showProgressBarEvent.postValue(true)
    protected fun hideProgressBar() = showProgressBarEvent.postValue(false)
}