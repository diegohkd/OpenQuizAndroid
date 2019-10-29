package mobdao.com.openquiz.data.utils.disposables

import java.util.concurrent.locks.ReentrantLock

// Strategy abstract class of Strategy pattern
abstract class Disposable {

    open fun dispose() {
        isDisposed = true
    }

    private val reentrantLock = ReentrantLock()

    internal open var isDisposed: Boolean = false
        get() {
            reentrantLock.lock()
            try {
                return field
            } finally {
                reentrantLock.unlock()
            }
        }
        set(value) {
            reentrantLock.lock()
            try {
                field = value
            } finally {
                reentrantLock.unlock()
            }
        }
}