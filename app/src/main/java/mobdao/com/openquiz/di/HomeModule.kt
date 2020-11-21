package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeViewModel(get(), get()) }
}
