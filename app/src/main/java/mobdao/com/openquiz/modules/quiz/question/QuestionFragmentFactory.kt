package mobdao.com.openquiz.modules.quiz.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import mobdao.com.openquiz.utils.factories.FragmentFactory

class QuestionFragmentFactory : FragmentFactory {

    override fun createFragment(bundle: Bundle?): Fragment {
        val fragment = QuestionFragment()
        bundle?.let { fragment.arguments = it }
        return fragment
    }
}
