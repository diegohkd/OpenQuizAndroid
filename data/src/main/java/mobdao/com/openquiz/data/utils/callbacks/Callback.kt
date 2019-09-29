package mobdao.com.openquiz.data.utils.callbacks

interface Callback<T> {
    fun onSuccess(result: T)
    fun onFailure(exception: Throwable?)
}