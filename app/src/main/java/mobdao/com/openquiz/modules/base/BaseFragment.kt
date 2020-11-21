package mobdao.com.openquiz.modules.base

import androidx.fragment.app.Fragment
import mobdao.com.openquiz.R
import mobdao.com.openquiz.utils.extensions.navigate
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.helpers.DialogHelper

abstract class BaseFragment : Fragment() {

    protected open val viewModel: BaseViewModel? = null

    @Suppress("RedundantLambdaArrow")
    protected fun setupGenericErrorObserver() = viewModel?.run {
        setupObserver(
            genericErrorEvent to { _ ->
                showDefaultErrorDialog()
            }
        )
    }

    protected fun setupNavigationObserver() = viewModel?.run {
        setupObserver(
            routeEvent to { direction ->
                navigate(direction)
            }
        )
    }

    // region private

    private fun showDefaultErrorDialog() {
        DialogHelper.buildDialog(
            context ?: return,
            title = R.string.ops,
            message = R.string.something_wrong
        ).show()
    }

    // endregion
}
