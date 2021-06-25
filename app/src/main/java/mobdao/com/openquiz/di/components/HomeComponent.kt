package mobdao.com.openquiz.di.components

import dagger.Subcomponent
import mobdao.com.openquiz.data.di.UserAuthRepositoryModule
import mobdao.com.openquiz.di.modules.HomeModule
import mobdao.com.openquiz.di.modules.ViewModelsModule
import mobdao.com.openquiz.modules.home.HomeFragment

@Subcomponent(
    modules = [
        ViewModelsModule::class,
        UserAuthRepositoryModule::class,
        HomeModule::class,

    ]
)
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}
