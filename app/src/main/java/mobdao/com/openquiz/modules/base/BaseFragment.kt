package mobdao.com.openquiz.modules.base

import androidx.fragment.app.Fragment
import mobdao.com.openquiz.R
import mobdao.com.openquiz.utils.helpers.DialogHelper

abstract class BaseFragment : Fragment() {

    fun showDefaultErrorDialog() {
        DialogHelper.buildDialog(
            context ?: return,
            title = R.string.ops,
            message = R.string.something_wrong
        ).show()
    }
}