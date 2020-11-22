package mobdao.com.openquiz.utils.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.showFragmentOnContainer(@IdRes container: Int, fragment: Fragment) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.add(container, fragment)
    fragmentTransaction.commit()
}
