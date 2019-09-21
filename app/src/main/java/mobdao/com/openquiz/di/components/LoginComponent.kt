package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.UserAuthRepositoryModule
import mobdao.com.openquiz.di.modules.ViewModelModule
import mobdao.com.openquiz.modules.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        UserAuthRepositoryModule::class
    ]
)

interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}