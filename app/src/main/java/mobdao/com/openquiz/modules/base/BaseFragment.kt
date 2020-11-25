package mobdao.com.openquiz.modules.base

import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
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

    protected fun onBackPressed(callback: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback()
                }
            }
        )
    }

    protected fun showAlertDialog(
        title: String? = null,
        message: String? = null,
        @StringRes positiveButtonText: Int? = android.R.string.ok,
        @StringRes negativeButtonText: Int? = null,
        positiveCallback: (() -> Unit)? = null,
        negativeCallback: (() -> Unit)? = null,
        dismissCallback: (() -> Unit)? = null,
        cancelable: Boolean = true,
    ) {
        AlertDialog.Builder(requireContext()).apply {
            title?.let(::setTitle)
            message?.let(::setMessage)
            dismissCallback?.run { setOnDismissListener { invoke() } }
            positiveButtonText?.let {
                setPositiveButton(positiveButtonText) { dialog, _ ->
                    dialog.dismiss()
                    positiveCallback?.invoke()
                }
            }
            negativeButtonText?.let {
                setNegativeButton(negativeButtonText) { dialog, _ ->
                    dialog.dismiss()
                    negativeCallback?.invoke()
                }
            }
            setCancelable(cancelable)
        }.create().show()
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
