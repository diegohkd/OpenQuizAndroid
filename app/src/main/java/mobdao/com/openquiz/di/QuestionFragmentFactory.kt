package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.quiz.question.QuestionFragmentFactory
import mobdao.com.openquiz.utils.factories.FragmentFactory
import org.koin.dsl.module

val fragmentFactoryModule = module {
    factory<FragmentFactory> { QuestionFragmentFactory() }
}