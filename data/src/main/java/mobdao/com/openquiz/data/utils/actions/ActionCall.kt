package mobdao.com.openquiz.data.utils.actions

import mobdao.com.openquiz.data.utils.Callback
import mobdao.com.openquiz.data.utils.extensions.runService
import retrofit2.Call

internal class ActionCall<T>(private val call: Call<T>) : Action<T> {

    override fun run(callback: Callback<T>?) {
        callback?.let { call.runService(callback) }
    }

    override fun cancel() {
        call.cancel()
    }
}
