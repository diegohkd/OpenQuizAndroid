package mobdao.com.openquiz.utils.extensions

import android.widget.RadioButton
import android.widget.RadioGroup

val RadioGroup.selectedRadioButton: RadioButton?
    get() = if (checkedRadioButtonId != -1) {
        findViewById(checkedRadioButtonId)
    } else {
        null
    }

fun RadioGroup.getRadioButtonAt(index: Int): RadioButton? = getChildAt(index) as? RadioButton
