package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.callbacks.Callback
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
        lateinit var disposableStrategy: DisposableStrategy
        lateinit var callbackRequest: Callback<T>
        callbackRequest = Callback(
            success = { result ->
                try {
                    mapper(result)
                } catch (exception: Exception) {
                    callbackRequest.failure?.invoke(exception)
                    null
                }?.run {
                    action = actionBase
                    disposableStrategy.disposable = subscribeBy(callback)
                }
            },
            failure = { exception ->
                callback.failure?.invoke(exception)
                disposableStrategy.dispose()
            }
        )
        disposableStrategy = DisposableStrategy(
            previousSingle.subscribeBy(callbackRequest)
        )
        return disposableStrategy
    }
}