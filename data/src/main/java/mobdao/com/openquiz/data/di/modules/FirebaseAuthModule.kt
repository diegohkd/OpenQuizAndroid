package mobdao.com.openquiz.data.di.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton

@Module
abstract class FirebaseAuthModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()
    }

}