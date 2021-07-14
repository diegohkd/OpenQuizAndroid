package mobdao.com.openquiz.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import mobdao.com.openquiz.modules.quiz.question.QuestionFragmentFactory
import mobdao.com.openquiz.utils.factories.FragmentFactory

@Module
@InstallIn(FragmentComponent::class)
abstract class QuizModule {

    companion object {
        @Provides
        fun fragmentFactory(): FragmentFactory = QuestionFragmentFactory()
    }
}
