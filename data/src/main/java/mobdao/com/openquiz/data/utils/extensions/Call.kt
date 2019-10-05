package mobdao.com.openquiz.data.utils.extensions

import mobdao.com.openquiz.data.utils.actions.ActionCall
import mobdao.com.openquiz.data.utils.singles.Single
import mobdao.com.openquiz.data.utils.singles.SingleSingle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal fun <T> Call<T>.runService(
    callback: mobdao.com.openquiz.data.utils.callbacks.Callback<T>
) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, throwable: Throwable?) {
            if (call?.isCanceled == true) return

            callback.failure?.invoke(throwable)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.isSuccessful == true) {
                response.body()?.let { callback.success?.invoke(it) }
            } else {
                val exception = response?.let { HttpException(it) } ?: Exception()
                callback.failure?.invoke(exception)
            }
        }
    })
}

internal fun <T> Call<T>.toSingle(): Single<T> =
    SingleSingle(ActionCall(this))