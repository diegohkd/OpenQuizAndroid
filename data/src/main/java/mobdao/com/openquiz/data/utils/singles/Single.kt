package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.Callback
import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.actions.ActionJust
import mobdao.com.openquiz.data.utils.disposables.Disposable

abstract class Single<T> {

    internal abstract val actionBase: Action<T>

    abstract fun subscribeBy(
        callback: Callback<T>
    ): Disposable

    fun <R> flatMap(mapper: (T) -> Single<R>): Single<R> {
        return SingleFlatMap(this, mapper)
    }

    companion object {
        fun <T> just(value: T): Single<T> =
            SingleSingle(ActionJust(value))
    }
}