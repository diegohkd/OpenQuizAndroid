package mobdao.com.openquiz.data.utils

internal class DisposableStrategy(
    var disposable: DisposableInterface
) : DisposableInterface {
    override fun dispose() {
        disposable.dispose()
    }
}
