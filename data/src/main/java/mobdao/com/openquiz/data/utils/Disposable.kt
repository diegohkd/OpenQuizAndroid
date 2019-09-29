package mobdao.com.openquiz.data.utils

class Disposable<T>(
    private var action: Action<T>?,
    private var callbackRequest: Callback<T>? = null,
    private var callbackObserver: Callback<T>? = null
): DisposableInterface {
    override fun dispose() {
        action?.cancel()
        callbackRequest = null
        callbackObserver = null
    }
}