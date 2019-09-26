package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.FirebaseAuthModule
import mobdao.com.openquiz.di.modules.SplashViewModelModule
import mobdao.com.openquiz.modules.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SplashViewModelModule::class,
        FirebaseAuthModule::class
    ]
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
