package mobdao.com.openquiz.modules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val showProgressBarEvent: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    protected fun showProgressBar() = showProgressBarEvent.postValue(true)
    protected fun hideProgressBar() = showProgressBarEvent.postValue(false)
}