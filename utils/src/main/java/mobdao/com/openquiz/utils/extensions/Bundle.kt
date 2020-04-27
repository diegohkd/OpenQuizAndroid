package mobdao.com.openquiz.utils.extensions

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.safeGetParcelable(key: String): T? =
    takeIf { it.containsKey(key) }?.getParcelable(key)