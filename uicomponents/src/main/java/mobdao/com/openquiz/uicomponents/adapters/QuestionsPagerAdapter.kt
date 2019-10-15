package mobdao.com.openquiz.uicomponents.adapters

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.utils.constants.IntentConstants.QUESTION
import mobdao.com.openquiz.utils.factories.FragmentFactory

class QuestionsPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragmentFactory: FragmentFactory
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var questions: List<Question> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int) =
        fragmentFactory.createFragment(Bundle().apply {
            putParcelable(
                QUESTION,
                questions[position]
            )
        })

    override fun getCount() = questions.size
}
