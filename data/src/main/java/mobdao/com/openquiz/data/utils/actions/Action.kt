package mobdao.com.openquiz.data.utils.actions

import mobdao.com.openquiz.data.utils.Callback

internal interface Action<T> {
    fun run(callback: Callback<T>?)
    fun cancel()
}
