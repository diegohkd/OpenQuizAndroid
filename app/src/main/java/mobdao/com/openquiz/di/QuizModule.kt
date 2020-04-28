package mobdao.com.openquiz.di

import mobdao.com.openquiz.modules.quiz.QuizViewModel
import org.koin.dsl.module

val quizModule = module {
    factory { QuizViewModel() }
}