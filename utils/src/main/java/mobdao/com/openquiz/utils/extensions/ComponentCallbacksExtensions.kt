package mobdao.com.openquiz.utils.extensions

import android.content.ComponentCallbacks
import org.koin.core.scope.Scope

@Suppress("unused")
inline fun <reified T : Any> ComponentCallbacks.injectOrNull(
    scope: Scope
) = lazy(LazyThreadSafetyMode.NONE) { scope.getOrNull<T>() }
