package mobdao.com.openquiz.utils.factories

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentFactory {
    fun createFragment(bundle: Bundle?): Fragment
}
