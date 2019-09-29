package mobdao.com.openquiz.data.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

fun <T> Call<T>.runService(
    callback: mobdao.com.openquiz.data.utils.Callback<T>
) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, throwable: Throwable?) {
            if (call?.isCanceled == true) return

            callback.onFailure(throwable)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.isSuccessful == true) {
                response.body()?.let { callback.onSuccess(it) }
            } else {
                val exception = response?.let { HttpException(it) } ?: Exception()
                callback.onFailure(exception)
            }
        }
    })
}

fun <T> Call<T>.toBaseSingle(): BaseSingle<T> =
    Single(ActionCall(this))