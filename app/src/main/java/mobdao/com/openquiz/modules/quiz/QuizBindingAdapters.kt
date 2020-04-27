package mobdao.com.openquiz.modules.quiz

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import mobdao.com.openquiz.models.Question
import mobdao.com.openquiz.uicomponents.adapters.QuestionsPagerAdapter
import mobdao.com.openquiz.utils.factories.FragmentFactory

@BindingAdapter(
    value = ["bind:questions", "bind:fragmentManager", "bind:fragmentFactory"],
    requireAll = true
)
fun bindAnswersFragment(
    viewPager: ViewPager,
    questions: List<Question>?,
    fragmentManager: FragmentManager?,
    fragmentFactory: FragmentFactory?
) {
    if (questions == null || fragmentManager == null || fragmentFactory == null) return
    viewPager.adapter = QuestionsPagerAdapter(fragmentManager, fragmentFactory, questions)
}

@BindingAdapter("bind:showNextPage")
fun bindNextItem(viewPager: ViewPager, showNextPage: Unit?) {
    viewPager.setCurrentItem(viewPager.currentItem + 1, true)
}
