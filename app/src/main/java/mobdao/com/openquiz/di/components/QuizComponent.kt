package mobdao.com.openquiz.di.components

import dagger.Subcomponent
import mobdao.com.openquiz.di.modules.QuizModule
import mobdao.com.openquiz.di.scopes.FragmentScope
import mobdao.com.openquiz.modules.quiz.QuizFragment
import mobdao.com.openquiz.modules.quiz.question.QuestionFragment
import mobdao.com.openquiz.modules.quiz.question.answers.BaseAnswersFragment

@Subcomponent(modules = [QuizModule::class])
@FragmentScope
interface QuizComponent {

    fun inject(quizFragment: QuizFragment)
    fun inject(questionFragment: QuestionFragment)
    fun inject(baseAnswersFragment: BaseAnswersFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuizComponent
    }
}
