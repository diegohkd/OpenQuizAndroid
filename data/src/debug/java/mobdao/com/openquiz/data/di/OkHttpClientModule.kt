package mobdao.com.openquiz.data.di

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val okHttpClientModule = module {
    single {
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("HttpLoggingInterceptor", message)
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single<OkHttpClient?> {
        OkHttpClient.Builder().run {
            addNetworkInterceptor(get<HttpLoggingInterceptor>())
            build()
        }
    }
}
