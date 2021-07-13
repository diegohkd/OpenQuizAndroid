package mobdao.com.openquiz.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapperImpl
import mobdao.com.openquiz.data.utils.constants.Constants.BASE_OPEN_TRIVIA_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class OpenTriviaRepositoryModule {

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
