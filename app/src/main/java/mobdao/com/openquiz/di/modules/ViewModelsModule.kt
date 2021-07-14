package mobdao.com.openquiz.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mobdao.com.openquiz.di.ViewModelKey
import mobdao.com.openquiz.di.scopes.FragmentScope
import mobdao.com.openquiz.modules.home.HomeViewModel
import mobdao.com.openquiz.modules.login.LoginViewModel
import mobdao.com.openquiz.modules.quiz.QuizViewModel
import mobdao.com.openquiz.modules.splash.SplashViewModel

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @FragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun quizViewModel(viewModel: QuizViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel
}
