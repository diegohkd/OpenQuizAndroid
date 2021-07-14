package mobdao.com.openquiz.data.di

import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapperImpl
import mobdao.com.openquiz.data.utils.constants.Constants.BASE_OPEN_TRIVIA_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpenTriviaRepositoryModule {

    @Binds
    @Singleton
    abstract fun openTriviaRepository(impl: OpenTriviaRepositoryImpl): OpenTriviaRepository

    companion object {
        @Singleton
        @Provides
        fun retrofit(
            converterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient?
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_OPEN_TRIVIA_URL)
                .addConverterFactory(converterFactory).apply {
                    okHttpClient?.let(::client)
                }
                .build()
        }

        @Provides
        fun questionServiceMapper(): QuestionServiceMapper = QuestionServiceMapperImpl()

        @Provides
        fun converterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create(GsonBuilder().create())
        }
    }
}
