package mobdao.com.openquiz.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.utils.disposables.Disposable
import mobdao.com.openquiz.di.scopes.AppSingleton

@Module
abstract class DisposableModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @AppSingleton
        fun provideDisposable(): Disposable? = null
    }
}