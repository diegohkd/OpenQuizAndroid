package mobdao.com.openquiz.data.utils.disposables

internal class DisposableStrategy(
    var disposable: Disposable
) : Disposable {
    override fun dispose() {
        disposable.dispose()
    }
}
