package mobdao.com.openquiz.di.modules

import dagger.Module
import mobdao.com.openquiz.di.components.HomeComponent
import mobdao.com.openquiz.di.components.LoginComponent
import mobdao.com.openquiz.di.components.QuizComponent
import mobdao.com.openquiz.di.components.SplashComponent

@Module(
    subcomponents = [
        SplashComponent::class,
        LoginComponent::class,
        HomeComponent::class,
        QuizComponent::class
    ]
)
class SubcomponentsModule
