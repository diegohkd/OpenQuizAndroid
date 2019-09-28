package mobdao.com.openquiz.data.utils

open class Single<T>(
    private var action: Action<T>
) : BaseSingle<T>() {

    override val actionBase: Action<T> = action

    override fun subscribeBy(
        callback: Callback<T>
    ): DisposableInterface {
        lateinit var disposable: Disposable<T>
        val callbackRequest = object : Callback<T> {
            override fun onSuccess(result: T) {
                callback.onSuccess(result)
                disposable.dispose()
            }

            override fun onFailure(exception: Throwable?) {
                callback.onFailure(exception)
                disposable.dispose()
            }
        }
        disposable = Disposable(action, callbackRequest, callback)
        action.run(callbackRequest)
        return DisposableStrategy(disposable)
    }
}