package mobdao.com.openquiz.data.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.di.scopes.DataSingleton
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProviderImpl

@Module
abstract class GoogleAuthProviderModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @DataSingleton
        fun provideGoogleAuthProvide(): GoogleAuthProvider =
            GoogleAuthProviderImpl()
    }
}