package mobdao.com.openquiz.data.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.modules.FirebaseAuthModule
import mobdao.com.openquiz.data.di.modules.RetrofitModule
import mobdao.com.openquiz.data.di.modules.UserAuthRepositoryModule
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.repositories.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository

@Component(
    modules = [
        RetrofitModule::class,
        FirebaseAuthModule::class,
        UserAuthRepositoryModule::class
    ]
)
@DataSingleton
interface DataComponent {
    fun openTriviaRepository(): OpenTriviaRepository
    fun userAuthRepository(): UserAuthRepository
}