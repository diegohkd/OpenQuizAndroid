package mobdao.com.openquiz.data.utils

import retrofit2.Call

class Disposable<T>(
    private var call: Call<T>?,
    private var callbackRequest: Callback<T>? = null,
    private var callbackObserver: Callback<T>? = null
) {
    fun dispose() {
        call?.cancel()
        callbackRequest = null
        callbackObserver = null
    }
}