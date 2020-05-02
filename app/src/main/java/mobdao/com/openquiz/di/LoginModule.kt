package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.login.LoginViewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginViewModel(get()) }
}