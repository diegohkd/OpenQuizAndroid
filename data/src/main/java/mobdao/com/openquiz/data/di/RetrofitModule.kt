package mobdao.com.openquiz.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import mobdao.com.openquiz.data.BuildConfig
import mobdao.com.openquiz.data.utils.constants.Constants.BASE_OPEN_TRIVIA_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single<GsonConverterFactory> {
        GsonConverterFactory.create(GsonBuilder().create())
    }
    single {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("HttpLoggingInterceptor", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder().run {
            val loggingInterceptor: HttpLoggingInterceptor = get()
            addNetworkInterceptor(loggingInterceptor)
            build()
        }
    }
    single<Retrofit> {
        val converterFactory: GsonConverterFactory = get()
        Retrofit.Builder()
            .baseUrl(BASE_OPEN_TRIVIA_URL)
            .addConverterFactory(converterFactory).apply {
                if (BuildConfig.DEBUG) client(get()) // add logging interceptor
            }
            .build()
    }
}
