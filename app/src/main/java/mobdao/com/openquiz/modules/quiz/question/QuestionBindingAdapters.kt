package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import mobdao.com.openquiz.R
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.models.QuestionType
import mobdao.com.openquiz.modules.quiz.question.answers.MultipleChoiceAnswersFragment
import mobdao.com.openquiz.modules.quiz.question.answers.TrueFalseAnswersFragment
import mobdao.com.openquiz.utils.constants.IntentConstants
import mobdao.com.openquiz.utils.extensions.showFragmentOnContainer

@BindingAdapter(value = ["bind:fragmentManager", "bind:question"], requireAll = true)
fun bindAnswersFragment(
    frameLayout: FrameLayout,
    fragmentManager: FragmentManager,
    question: Question
) {
    val fragment = if (question.type == QuestionType.MULTIPLE_CHOICE) {
        MultipleChoiceAnswersFragment()
    } else {
        TrueFalseAnswersFragment()
    }
    fragment.arguments = Bundle().apply { putParcelable(IntentConstants.QUESTION, question) }
    fragmentManager.showFragmentOnContainer(R.id.answersContainer, fragment)
}
