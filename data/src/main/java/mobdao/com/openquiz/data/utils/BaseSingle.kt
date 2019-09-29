package mobdao.com.openquiz.data.utils

abstract class BaseSingle<T> {

    internal abstract val actionBase: Action<T>

    abstract fun subscribeBy(
        callback: Callback<T>
    ): DisposableInterface

    fun <R> flatMap(mapper: (T) -> BaseSingle<R>): BaseSingle<R> {
        return SingleFlatMap(this, mapper)
    }

    companion object {
        fun <T> just(value: T): Single<T> =
            Single(ActionJust(value))
    }
}