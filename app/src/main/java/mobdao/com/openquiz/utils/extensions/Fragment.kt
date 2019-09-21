package mobdao.com.openquiz.utils.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import mobdao.com.openquiz.modules.base.BaseFragment

fun BaseFragment.setupSingleEventObserver(
    pair: Pair<MutableLiveData<Unit>, () -> Unit>
) = pair.first.observe(this, Observer { pair.second() })