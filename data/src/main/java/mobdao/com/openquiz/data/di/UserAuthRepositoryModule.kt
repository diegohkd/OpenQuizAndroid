package mobdao.com.openquiz.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuthImpl
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserAuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun userAuthRepository(impl: UserAuthRepositoryImpl): UserAuthRepository

    companion object {
        @Provides
        fun firebaseAuth(): FirebaseAuth = FirebaseAuthImpl()

        @Provides
        fun googleAuthProvider(): GoogleAuthProvider = GoogleAuthProviderImpl()
    }
}
