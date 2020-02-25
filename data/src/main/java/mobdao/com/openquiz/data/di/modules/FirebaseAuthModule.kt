package mobdao.com.openquiz.data.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuthImpl

@Module
abstract class FirebaseAuthModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuthImpl()
    }
}