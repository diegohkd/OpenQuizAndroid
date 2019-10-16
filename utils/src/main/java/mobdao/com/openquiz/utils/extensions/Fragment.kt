package mobdao.com.openquiz.utils.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*

fun <T> Fragment.setupObserver(
    pair: Pair<LiveData<T>, (T) -> Unit>
) = pair.first.observe(viewLifecycleOwner, Observer { it?.let(pair.second) })

fun Fragment.setupSingleEventObserver(
    pair: Pair<MutableLiveData<Unit>, () -> Unit?>
) = pair.first.observe(viewLifecycleOwner, Observer { pair.second() })

@MainThread
inline fun <reified VM : ViewModel> Fragment.sharedViewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { requireActivity() },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)