package mobdao.com.openquiz.data.di.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl

@Module
abstract class UserAuthRepositoryModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideUserAuthRepository(firebaseAuth: FirebaseAuth): UserAuthRepository =
            UserAuthRepositoryImpl(firebaseAuth)
    }
}