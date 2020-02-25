package mobdao.com.openquiz.data.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import retrofit2.Retrofit

@Module
abstract class OpenTriviaRepositoryModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideOpenTriviaRepository(
            retrofit: Retrofit,
            questionServiceMapper: QuestionServiceMapper
        ): OpenTriviaRepository =
            OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
    }
}