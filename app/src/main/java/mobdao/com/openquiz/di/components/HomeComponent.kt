package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.components.DataComponent
import mobdao.com.openquiz.di.modules.DisposableModule
import mobdao.com.openquiz.di.modules.HomeViewModelModule
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.modules.home.HomeFragment

@Component(
    modules = [HomeViewModelModule::class,
        DisposableModule::class
    ],
    dependencies = [DataComponent::class]
)
@AppSingleton
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}