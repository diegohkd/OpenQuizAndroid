package mobdao.com.openquiz.data.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.modules.*
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository

@Component(
    modules = [
        RetrofitModule::class,
        FirebaseAuthModule::class,
        UserAuthRepositoryModule::class,
        GoogleAuthProviderModule::class,
        OpenTriviaRepositoryModule::class,
        QuestionServiceMapperModule::class
    ]
)
@DataSingleton
interface DataComponent {
    fun openTriviaRepository(): OpenTriviaRepository
    fun userAuthRepository(): UserAuthRepository
}