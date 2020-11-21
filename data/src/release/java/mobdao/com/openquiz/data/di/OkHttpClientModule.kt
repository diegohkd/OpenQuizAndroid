package mobdao.com.openquiz.data.di

import okhttp3.OkHttpClient
import org.koin.dsl.module

val okHttpClientModule = module {
    single<OkHttpClient?> { null }
}
