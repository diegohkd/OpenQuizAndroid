package mobdao.com.openquiz.data.utils.callbacks

data class Callback<T> (
    var success: ((T) -> Unit)?,
    var failure: ((Throwable?) -> Unit)? = null
) {
    fun reset() {
        success = null
        failure = null
    }
}