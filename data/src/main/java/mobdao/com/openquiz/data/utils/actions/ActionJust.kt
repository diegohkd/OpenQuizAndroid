package mobdao.com.openquiz.data.utils.actions

import mobdao.com.openquiz.data.utils.callbacks.Callback

internal class ActionJust<T>(private val value: T) : Action<T> {

    override fun run(callback: Callback<T>?) {
        callback?.success?.invoke(value)
    }

    override fun cancel() {}
}
