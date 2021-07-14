package mobdao.com.openquiz.data.di

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuthImpl
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProviderImpl

@Module
abstract class UserAuthRepositoryModule {

    companion object {
        @Provides
        fun firebaseAuth(): FirebaseAuth = FirebaseAuthImpl()

        @Provides
        fun googleAuthProvider(): GoogleAuthProvider = GoogleAuthProviderImpl()
    }
}
