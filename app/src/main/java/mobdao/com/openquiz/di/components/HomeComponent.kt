package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.FirebaseAuthModule
import mobdao.com.openquiz.di.modules.HomeViewModelModule
import mobdao.com.openquiz.modules.home.HomeFragment
import javax.inject.Singleton

// TODO will this make view model also singleton?
@Singleton
@Component(
    modules = [
        HomeViewModelModule::class,
        FirebaseAuthModule::class
    ]
)
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}