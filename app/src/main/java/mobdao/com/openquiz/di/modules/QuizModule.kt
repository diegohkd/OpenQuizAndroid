package mobdao.com.openquiz.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.modules.quiz.question.QuestionFragmentFactory
import mobdao.com.openquiz.utils.factories.FragmentFactory

@Module
abstract class QuizModule {

    companion object {
        @Provides
        fun fragmentFactory(): FragmentFactory = QuestionFragmentFactory()
    }
}
