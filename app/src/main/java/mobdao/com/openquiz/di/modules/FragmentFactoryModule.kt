package mobdao.com.openquiz.di.modules

import dagger.Binds
import dagger.Module
import mobdao.com.openquiz.modules.quiz.question.QuestionFragmentFactory
import mobdao.com.openquiz.utils.factories.FragmentFactory

@Module
abstract class FragmentFactoryModule {

    @Binds
    abstract fun bindFragmentFactory(fragmentFactory: QuestionFragmentFactory): FragmentFactory
}