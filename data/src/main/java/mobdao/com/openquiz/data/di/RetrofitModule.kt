package mobdao.com.openquiz.data.di

import com.google.gson.GsonBuilder
import mobdao.com.openquiz.data.utils.constants.Constants.BASE_OPEN_TRIVIA_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single<GsonConverterFactory> {
        GsonConverterFactory.create(GsonBuilder().create())
    }
    single<Retrofit> {
        val converterFactory: GsonConverterFactory = get()
        val okHttpClient: OkHttpClient? = getOrNull()
        Retrofit.Builder()
            .baseUrl(BASE_OPEN_TRIVIA_URL)
            .addConverterFactory(converterFactory).apply {
                okHttpClient?.let(::client) // add logging interceptor
            }
            .build()
    }
}
