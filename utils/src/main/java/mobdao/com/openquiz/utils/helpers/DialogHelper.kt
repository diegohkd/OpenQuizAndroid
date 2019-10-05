package mobdao.com.openquiz.utils.helpers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import androidx.annotation.StringRes

object DialogHelper {

    fun buildDialog(
        context: Context,
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        @StringRes positiveButtonText: Int? = null,
        positiveCallback: (() -> Unit)? = null,
        dismissCallback: (() -> Unit)? = null
    ): Dialog =
        makeDialogBuilder(
            context,
            title?.let(context::getString),
            message?.let(context::getString),
            positiveButtonText ?: android.R.string.ok,
            positiveCallback,
            dismissCallback
        ).create()

    // region private

    private fun makeDialogBuilder(
        context: Context,
        title: String? = null,
        message: String? = null,
        @StringRes positiveButtonText: Int = android.R.string.ok,
        positiveCallback: (() -> Unit)? = null,
        dismissCallback: (() -> Unit)? = null
    ): AlertDialog.Builder =
        AlertDialog.Builder(context).apply {
            title?.let(::setTitle)
            message?.let(::setMessage)
            dismissCallback?.run { setOnDismissListener { invoke() } }
            setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                positiveCallback?.invoke()
            }
        }

    // endregion
}