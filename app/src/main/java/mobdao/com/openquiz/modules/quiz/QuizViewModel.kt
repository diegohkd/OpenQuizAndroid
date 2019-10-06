package mobdao.com.openquiz.modules.quiz

import android.util.Log
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.modules.base.BaseViewModel
import javax.inject.Inject

class QuizViewModel @Inject constructor() : BaseViewModel() {

    fun init(questions: List<Question>) {

    }

    fun onConfirmClicked() {
        Log.d("aaaa", "confirm clicked")
    }

}