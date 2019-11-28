package mobdao.com.openquiz.data.utils.disposables

// Context of Strategy pattern
internal class DisposableContext(
    var disposable: Disposable
) : Disposable() {

    override var isDisposed: Boolean
        get() = disposable.isDisposed
        set(value) {
            disposable.isDisposed = value
        }

    override fun dispose() {
        disposable.dispose()
    }
}
