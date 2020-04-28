package mobdao.com.openquiz.data.di

import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProviderImpl
import org.koin.dsl.module

val googleAuthProviderModule = module {
    single<GoogleAuthProvider> { GoogleAuthProviderImpl() }
}