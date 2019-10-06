package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.QuizViewModelModule
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.modules.quiz.QuizFragment

@Component(
    modules = [QuizViewModelModule::class]
)
@AppSingleton
interface QuizComponent {
    fun inject(quizFragment: QuizFragment)
}