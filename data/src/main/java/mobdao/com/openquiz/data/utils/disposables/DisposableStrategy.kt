package mobdao.com.openquiz.data.utils.disposables

internal class DisposableStrategy(
    var disposable: DisposableInterface
) : DisposableInterface {
    override fun dispose() {
        disposable.dispose()
    }
}
