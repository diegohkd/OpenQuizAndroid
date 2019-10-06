package mobdao.com.openquiz.modules.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_quiz.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.di.components.DaggerQuizComponent
import mobdao.com.openquiz.modules.base.BaseFragment
import mobdao.com.openquiz.modules.quiz.question.QuestionFragmentFactory
import mobdao.com.openquiz.uicomponents.adapters.QuestionsPagerAdapter
import mobdao.com.openquiz.utils.extensions.setupObserver
import mobdao.com.openquiz.utils.extensions.setupSingleEventObserver
import mobdao.com.openquiz.utils.extensions.sharedViewModel
import javax.inject.Inject

class QuizFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: QuizViewModel by sharedViewModel { viewModelFactory }
    private val args: QuizFragmentArgs by navArgs()

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_quiz, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjections()
        setupObservers()
        viewModel.init(args.questions.toList())
    }

    //endregion

    //region private

    private fun setupInjections() {
        DaggerQuizComponent
            .create()
            .inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        setupObserver(questionsLiveData to { questions ->
            fragmentManager?.let { fm ->
                viewPager.adapter = QuestionsPagerAdapter(fm, QuestionFragmentFactory(), questions)
            }
        })

        setupSingleEventObserver(showNextQuestionEvent to {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        })
    }

    //endregion
}