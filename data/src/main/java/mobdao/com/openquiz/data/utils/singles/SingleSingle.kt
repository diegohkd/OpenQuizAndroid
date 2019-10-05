package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.disposables.DisposableImpl
import mobdao.com.openquiz.data.utils.disposables.DisposableStrategy

internal class SingleSingle<T>(
    private var action: Action<T>
) : Single<T>() {

    override val actionBase: Action<T> = action

    override fun subscribeBy(
        callback: Callback<T>
    ): Disposable {
        lateinit var disposableStrategy: DisposableStrategy
        val callbackRequest = Callback<T>(
            success = { result ->
                callback.success?.invoke(result)
                disposableStrategy.dispose()
            },
            failure = { exception ->
                callback.failure?.invoke(exception)
                disposableStrategy.dispose()
            }
        )
        disposableStrategy = DisposableStrategy(DisposableImpl(action, callbackRequest, callback))

        try {
            action.run(callbackRequest)
        } catch (exception: Exception) {
            callbackRequest.failure?.invoke(exception)
        }
        return disposableStrategy
    }
}