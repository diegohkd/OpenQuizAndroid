package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.FirebaseAuthModule
import mobdao.com.openquiz.di.modules.ViewModelModule
import mobdao.com.openquiz.modules.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        FirebaseAuthModule::class
    ]
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
