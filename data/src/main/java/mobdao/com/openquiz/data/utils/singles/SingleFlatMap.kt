package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.Callback
import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.disposables.DisposableStrategy

internal class SingleFlatMap<T, R>(
    private val previousSingle: Single<T>,
    private val mapper: (T) -> Single<R>
) : Single<R>() {

    private lateinit var action: Action<R>
    override val actionBase: Action<R> get() = action

    override fun subscribeBy(
        callback: Callback<R>
    ): Disposable {
        lateinit var disposable: DisposableStrategy
        val callbackRequest = object : Callback<T> {
            override fun onSuccess(result: T) {
                mapper(result).apply {
                    action = actionBase
                    disposable.disposable = subscribeBy(callback)
                }
            }

            override fun onFailure(exception: Throwable?) {
                callback.onFailure(exception)
                disposable.dispose()
            }
        }
        disposable = DisposableStrategy(
            previousSingle.subscribeBy(callbackRequest)
        )
        return disposable
    }
}