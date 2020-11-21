package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.splash.SplashViewModel
import org.koin.dsl.module

val splashModule = module {
    factory { SplashViewModel(get()) }
}
