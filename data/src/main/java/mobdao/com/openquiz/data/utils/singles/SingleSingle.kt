package mobdao.com.openquiz.data.utils.singles

import mobdao.com.openquiz.data.utils.Callback
import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.disposables.DisposableImpl
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.data.utils.disposables.DisposableStrategy

internal class SingleSingle<T>(
    private var action: Action<T>
) : Single<T>() {

    override val actionBase: Action<T> = action

    override fun subscribeBy(
        callback: Callback<T>
    ): Disposable {
        lateinit var disposable: DisposableImpl<T>
        val callbackRequest = object : Callback<T> {
            override fun onSuccess(result: T) {
                callback.onSuccess(result)
                disposable.dispose()
            }

            override fun onFailure(exception: Throwable?) {
                callback.onFailure(exception)
                disposable.dispose()
            }
        }
        disposable =
            DisposableImpl(action, callbackRequest, callback)
        action.run(callbackRequest)
        return DisposableStrategy(disposable)
    }
}