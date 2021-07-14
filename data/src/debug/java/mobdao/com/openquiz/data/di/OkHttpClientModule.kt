package mobdao.com.openquiz.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
abstract class OkHttpClientModule {

    companion object {
        @Provides
        fun okHttpClient(): OkHttpClient? {

            return OkHttpClient.Builder().run {
                addNetworkInterceptor(httpLoggingInterceptor())
                build()
            }
        }

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("HttpLoggingInterceptor", message)
                    }
                }
            ).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
}
