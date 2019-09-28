package mobdao.com.openquiz.di.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class FirebaseAuthModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()
    }
}