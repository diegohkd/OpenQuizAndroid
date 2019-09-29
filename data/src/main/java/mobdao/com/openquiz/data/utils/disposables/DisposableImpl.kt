package mobdao.com.openquiz.data.utils.disposables

import mobdao.com.openquiz.data.utils.callbacks.Callback
import mobdao.com.openquiz.data.utils.actions.Action

internal class DisposableImpl<T>(
    private var action: Action<T>?,
    private var callbackRequest: Callback<T>? = null,
    private var callbackObserver: Callback<T>? = null
): Disposable {
    override fun dispose() {
        action?.cancel()
        callbackRequest = null
        callbackObserver = null
    }
}