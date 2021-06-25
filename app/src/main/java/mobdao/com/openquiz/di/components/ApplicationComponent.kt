package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.data.di.OkHttpClientModule
import mobdao.com.openquiz.data.di.OpenTriviaRepositoryModule
import mobdao.com.openquiz.di.modules.SubcomponentsModule
import javax.inject.Singleton

@Component(
    modules = [
        SubcomponentsModule::class,
        OpenTriviaRepositoryModule::class,
        OkHttpClientModule::class
    ]
)
@Singleton
interface ApplicationComponent {
    fun splashComponent(): SplashComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun homeComponent(): HomeComponent.Factory
    fun quizComponent(): QuizComponent.Factory
}
