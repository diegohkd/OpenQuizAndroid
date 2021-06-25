package mobdao.com.openquiz.data.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
abstract class OkHttpClientModule {
    companion object {
        @Provides
        fun okHttpClient(): OkHttpClient? = null
    }
}
