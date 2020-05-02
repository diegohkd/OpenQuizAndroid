package mobdao.com.openquiz.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun <T> Fragment.setupObserver(
    pair: Pair<LiveData<T>, (T) -> Unit>
) = pair.first.observe(viewLifecycleOwner, Observer { it?.let(pair.second) })

fun Fragment.setupSingleEventObserver(
    pair: Pair<MutableLiveData<Unit>, () -> Unit?>
) = pair.first.observe(viewLifecycleOwner, Observer { pair.second() })

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}