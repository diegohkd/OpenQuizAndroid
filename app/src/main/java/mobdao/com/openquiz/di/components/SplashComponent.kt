package mobdao.com.openquiz.di.components

import dagger.Subcomponent
import mobdao.com.openquiz.data.di.UserAuthRepositoryModule
import mobdao.com.openquiz.di.modules.SplashModule
import mobdao.com.openquiz.di.modules.ViewModelsModule
import mobdao.com.openquiz.modules.splash.SplashActivity

@Subcomponent(
    modules = [
        ViewModelsModule::class,
        UserAuthRepositoryModule::class,
        SplashModule::class
    ]
)
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SplashComponent
    }
}
