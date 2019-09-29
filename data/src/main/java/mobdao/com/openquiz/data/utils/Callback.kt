package mobdao.com.openquiz.data.utils

interface Callback<T> {
    fun onSuccess(result: T)
    fun onFailure(exception: Throwable?)
}