package mobdao.com.openquiz.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider

@Module
abstract class SplashModule {

    companion object {
        @Provides
        fun userAuthRepository(
            firebaseAuth: FirebaseAuth,
            googleAuthProvider: GoogleAuthProvider
        ): UserAuthRepository {
            return UserAuthRepositoryImpl(firebaseAuth, googleAuthProvider)
        }
    }
}
