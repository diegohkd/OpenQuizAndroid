package mobdao.com.openquiz.data.di

import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuthImpl
import org.koin.dsl.module

val firebaseAuthModule = module {
    single<FirebaseAuth> { FirebaseAuthImpl() }
}
