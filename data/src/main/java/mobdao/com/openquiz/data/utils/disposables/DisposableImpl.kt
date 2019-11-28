package mobdao.com.openquiz.data.utils.disposables

import mobdao.com.openquiz.data.utils.actions.Action
import mobdao.com.openquiz.data.utils.callbacks.Callback

internal class DisposableImpl<T>(
    private var action: Action<T>?,
    private var callbackRequest: Callback<T>? = null,
    private var callbackObserver: Callback<T>? = null
) : Disposable() {

    override fun dispose() {
        action?.cancel()
        callbackRequest?.reset()
        callbackObserver?.reset()
        callbackRequest = null
        callbackObserver = null
        super.dispose()
    }
}