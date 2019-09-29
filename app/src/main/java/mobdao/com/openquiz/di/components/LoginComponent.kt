package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.components.DataComponent
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.di.modules.LoginViewModelModule
import mobdao.com.openquiz.modules.login.LoginFragment

@Component(
    modules = [LoginViewModelModule::class],
    dependencies = [DataComponent::class]
)
@AppSingleton
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}