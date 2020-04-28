package mobdao.com.openquiz.data.di

import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import org.koin.dsl.module

val userAuthRepositoryModule = module {
    single<UserAuthRepository> { UserAuthRepositoryImpl(get(), get()) }
}