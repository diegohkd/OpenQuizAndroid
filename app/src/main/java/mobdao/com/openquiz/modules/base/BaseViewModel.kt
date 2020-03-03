package mobdao.com.openquiz.modules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobdao.com.openquiz.utils.livedata.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val showProgressBarEvent: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val genericErrorEvent: SingleLiveEvent<Unit> by lazy { SingleLiveEvent<Unit>() }

    protected fun showProgressBar() = showProgressBarEvent.postValue(true)
    protected fun hideProgressBar() = showProgressBarEvent.postValue(false)
}