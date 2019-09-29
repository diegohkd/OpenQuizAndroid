package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.components.DataComponent
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.di.modules.SplashViewModelModule
import mobdao.com.openquiz.modules.splash.SplashActivity

@Component(
    modules = [SplashViewModelModule::class],
    dependencies = [DataComponent::class]
)
@AppSingleton
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
