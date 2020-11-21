package mobdao.com.openquiz.modules.quiz.question.answers

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.updateMargins
import androidx.databinding.BindingAdapter
import mobdao.com.openquiz.utils.extensions.fromHtml
import mobdao.com.openquiz.utils.helpers.DimensionHelper

@BindingAdapter("bind:answersOptions")
fun bindAnswersFragment(radioGroup: RadioGroup, answersOptions: List<String>) {
    val context = radioGroup.context
    answersOptions.forEach { option ->
        val radioButton = RadioButton(context)
        radioButton.layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.MATCH_PARENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            updateMargins(
                top = DimensionHelper.convertDpToPixel(16f, context).toInt()
            )
        }
        radioButton.text = option.fromHtml()
        radioGroup.addView(radioButton)
    }
}
