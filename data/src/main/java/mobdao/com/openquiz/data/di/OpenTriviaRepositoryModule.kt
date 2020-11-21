package mobdao.com.openquiz.data.di

import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import org.koin.dsl.module

val openTriviaRepositoryModule = module {
    single<OpenTriviaRepository> { OpenTriviaRepositoryImpl(get(), get()) }
}
