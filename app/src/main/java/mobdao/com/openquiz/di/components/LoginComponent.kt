package mobdao.com.openquiz.di.components

import dagger.Subcomponent
import mobdao.com.openquiz.data.di.UserAuthRepositoryModule
import mobdao.com.openquiz.di.modules.LoginModule
import mobdao.com.openquiz.di.modules.ViewModelsModule
import mobdao.com.openquiz.modules.login.LoginFragment

@Subcomponent(
    modules = [
        ViewModelsModule::class,
        UserAuthRepositoryModule::class,
        LoginModule::class
    ]
)
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }
}
