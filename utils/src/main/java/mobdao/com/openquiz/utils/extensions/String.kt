package mobdao.com.openquiz.utils.extensions

import androidx.core.text.HtmlCompat

fun String.fromHtml() = HtmlCompat.fromHtml(
    this,
    HtmlCompat.FROM_HTML_MODE_LEGACY
)