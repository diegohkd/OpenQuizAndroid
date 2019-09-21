package mobdao.com.openquiz.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T> AppCompatActivity.setupObserver(
    pair: Pair<LiveData<T>, (T) -> Unit>
) = pair.first.observe(this, Observer { it?.let(pair.second) })