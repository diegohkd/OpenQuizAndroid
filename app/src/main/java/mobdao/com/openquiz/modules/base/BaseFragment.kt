package mobdao.com.openquiz.modules.base

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import mobdao.com.openquiz.R
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.helpers.DialogHelper

abstract class BaseFragment : Fragment() {

    protected open val viewModel: BaseViewModel? = null

    protected fun setupProgressBarObserver(progressBar: ProgressBar) = viewModel?.run {
        setupObserver(showProgressBarEvent to { showLoading ->
            progressBar.isVisible = showLoading
        })
    }

    fun showDefaultErrorDialog() {
        DialogHelper.buildDialog(
            context ?: return,
            title = R.string.ops,
            message = R.string.something_wrong
        ).show()
    }
}