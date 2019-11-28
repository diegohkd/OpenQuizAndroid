package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.disposables.DisposableContext
import mobdao.com.openquiz.data.utils.disposables.DisposableImpl

internal class SingleSingle<T>(
    private var action: Action<T>
) : Single<T>() {

    override val actionBase: Action<T> = action

    override fun subscribeBy(
        callback: Callback<T>
    ): Disposable {
        lateinit var disposableContext: DisposableContext
        val callbackRequest = Callback<T>(
            success = { result ->
                callback.success?.invoke(result)
                disposableContext.dispose()
            },
            failure = { exception ->
                callback.failure?.invoke(exception)
                disposableContext.dispose()
            }
        )
        disposableContext = DisposableContext(DisposableImpl(action, callbackRequest, callback))

        try {
            action.run(callbackRequest)
        } catch (exception: Exception) {
            callbackRequest.failure?.invoke(exception)
        }
        return disposableContext
    }
}