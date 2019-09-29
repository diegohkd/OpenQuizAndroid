package mobdao.com.openquiz.data.di.modules

import android.util.Log
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.utils.constants.Constants.BASE_OPEN_TRIVIA_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class RetrofitModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideRetrofit(
            callAdapterFactory: RxJavaCallAdapterFactory,
            gsonConverterFactory: GsonConverterFactory,
            client: OkHttpClient
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_OPEN_TRIVIA_URL)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build()

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideCallAdapterFactory(): RxJavaCallAdapterFactory =
            RxJavaCallAdapterFactory.create()

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideGsonConverterFactory(): GsonConverterFactory =
            GsonConverterFactory.create(GsonBuilder().create())

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideOkHttpClientBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder().run {
                addNetworkInterceptor(httpLoggingInterceptor)
                build()
            }

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("HttpLoggingInterceptor", message)
                }
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }
}
