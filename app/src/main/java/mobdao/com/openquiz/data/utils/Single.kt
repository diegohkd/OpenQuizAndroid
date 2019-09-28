package mobdao.com.openquiz.data.utils

import retrofit2.Call

open class Single<T>(
    private var call: Call<T>
) {
    fun subscribeBy(
        callback: Callback<T>
    ): Disposable<T> {
        lateinit var disposable: Disposable<T>
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
        disposable = Disposable(call, callbackRequest, callback)
        call.runService(callbackRequest)
        return disposable
    }
}