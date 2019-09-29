package mobdao.com.openquiz.data.utils

interface Action<T> {
    fun run(callback: Callback<T>?)
    fun cancel()
}
