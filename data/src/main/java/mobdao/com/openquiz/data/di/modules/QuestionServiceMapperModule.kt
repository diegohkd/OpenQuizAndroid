package mobdao.com.openquiz.data.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapperImpl

@Module
abstract class QuestionServiceMapperModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideQuestionServiceMapper(): QuestionServiceMapper =
            QuestionServiceMapperImpl()
    }
}