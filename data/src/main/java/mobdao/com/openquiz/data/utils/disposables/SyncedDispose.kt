package mobdao.com.openquiz.data.utils.disposables

import java.util.concurrent.locks.ReentrantLock

internal abstract class SyncedDispose {
    private val reentrantLock = ReentrantLock()

    var isDisposed: Boolean = false
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