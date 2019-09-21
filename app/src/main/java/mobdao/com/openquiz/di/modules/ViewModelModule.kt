package mobdao.com.openquiz.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mobdao.com.openquiz.di.keys.ViewModelKey
import mobdao.com.openquiz.modules.splash.SplashViewModel
import mobdao.com.openquiz.utils.factories.ViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindUserViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}