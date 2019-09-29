package mobdao.com.openquiz.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mobdao.com.openquiz.di.keys.ViewModelKey
import mobdao.com.openquiz.modules.home.HomeViewModel
import mobdao.com.openquiz.modules.login.LoginViewModel
import mobdao.com.openquiz.modules.splash.SplashViewModel
import mobdao.com.openquiz.di.factories.ViewModelFactory

@Module
abstract class BaseViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Suppress("unused")
@Module
abstract class SplashViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}

@Suppress("unused")
@Module
abstract class LoginViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}

@Suppress("unused")
@Module
abstract class HomeViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}