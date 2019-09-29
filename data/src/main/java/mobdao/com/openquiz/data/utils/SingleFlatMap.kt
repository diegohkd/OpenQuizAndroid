package mobdao.com.openquiz.data.utils

class SingleFlatMap<T, R>(
    private val previousBaseSingle: BaseSingle<T>,
    private val mapper: (T) -> BaseSingle<R>
) : BaseSingle<R>() {

    private lateinit var action: Action<R>
    override val actionBase: Action<R> get() = action

    override fun subscribeBy(
        callback: Callback<R>
    ): DisposableInterface {
        lateinit var disposable: DisposableStrategy
        val callbackRequest = object : Callback<T> {
            override fun onSuccess(result: T) {
                mapper(result).apply {
                    action = actionBase
                    disposable.disposable = subscribeBy(callback)
                }
            }

            override fun onFailure(exception: Throwable?) {
                callback.onFailure(exception)
                disposable.dispose()
            }
        }
        disposable = DisposableStrategy(previousBaseSingle.subscribeBy(callbackRequest))
        return disposable
    }
}