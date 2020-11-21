package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.quiz.QuizFragment.Companion.scopeName
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val quizModule = module {
    scope(named(scopeName)) {
        viewModel { QuizViewModel() }
    }
}
