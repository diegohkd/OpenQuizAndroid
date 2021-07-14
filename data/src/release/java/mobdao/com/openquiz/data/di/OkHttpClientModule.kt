package mobdao.com.openquiz.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
abstract class OkHttpClientModule {
    companion object {
        @Provides
        fun okHttpClient(): OkHttpClient? = null
    }
}
