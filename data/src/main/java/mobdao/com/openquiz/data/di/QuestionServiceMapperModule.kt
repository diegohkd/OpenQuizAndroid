package mobdao.com.openquiz.data.di

import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapperImpl
import org.koin.dsl.module

val questionServiceMapperModule = module {
    single<QuestionServiceMapper> { QuestionServiceMapperImpl() }
}
