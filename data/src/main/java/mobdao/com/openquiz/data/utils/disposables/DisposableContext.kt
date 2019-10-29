package mobdao.com.openquiz.data.utils.disposables

// Context of Strategy pattern
internal class DisposableContext(
    var disposable: Disposable
) : Disposable {
    override fun dispose() {
        disposable.dispose()
    }
}
