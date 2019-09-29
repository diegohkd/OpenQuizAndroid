package mobdao.com.openquiz.data.utils

import retrofit2.Call

class ActionCall<T>(private val call: Call<T>) : Action<T> {

    override fun run(callback: Callback<T>?) {
        callback?.let { call.runService(callback) }
    }

    override fun cancel() {
        call.cancel()
    }
}
