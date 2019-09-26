package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.FirebaseAuthModule
import mobdao.com.openquiz.di.modules.LoginViewModelModule
import mobdao.com.openquiz.modules.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LoginViewModelModule::class,
        FirebaseAuthModule::class
    ]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}