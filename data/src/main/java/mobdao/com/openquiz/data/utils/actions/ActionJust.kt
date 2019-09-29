package mobdao.com.openquiz.data.utils.actions

import mobdao.com.openquiz.data.utils.Callback

class ActionJust<T>(private val value: T) : Action<T> {

    override fun run(callback: Callback<T>?) {
        callback?.onSuccess(value)
    }

    override fun cancel() {}
}
