package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.QuizViewModelModule
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.modules.quiz.question.answers.MultipleChoiceAnswersFragment
import mobdao.com.openquiz.modules.quiz.question.QuestionFragment
import mobdao.com.openquiz.modules.quiz.question.answers.BaseAnswersFragment
import mobdao.com.openquiz.modules.quiz.question.answers.TrueFalseAnswersFragment

@Component(
    modules = [QuizViewModelModule::class]
)
@AppSingleton
interface QuestionComponent {
    fun inject(fragment: QuestionFragment)
    fun inject(fragment: BaseAnswersFragment)
}