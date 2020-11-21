package mobdao.com.openquiz

import android.app.Application
import mobdao.com.openquiz.data.di.firebaseAuthModule
import mobdao.com.openquiz.data.di.googleAuthProviderModule
import mobdao.com.openquiz.data.di.okHttpClientModule
import mobdao.com.openquiz.data.di.openTriviaRepositoryModule
import mobdao.com.openquiz.data.di.questionServiceMapperModule
import mobdao.com.openquiz.data.di.retrofitModule
import mobdao.com.openquiz.data.di.userAuthRepositoryModule
import mobdao.com.openquiz.di.fragmentFactoryModule
import mobdao.com.openquiz.di.homeModule
import mobdao.com.openquiz.di.loginModule
import mobdao.com.openquiz.di.quizModule
import mobdao.com.openquiz.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class OpenQuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    // region private

    private fun setupKoin() {
        startKoin {
            androidContext(this@OpenQuizApplication)
            modules(
                retrofitModule,
                okHttpClientModule,
                questionServiceMapperModule,
                openTriviaRepositoryModule,
                firebaseAuthModule,
                googleAuthProviderModule,
                userAuthRepositoryModule,
                homeModule,
                loginModule,
                splashModule,
                quizModule,
                fragmentFactoryModule
            )
        }
    }

    // endregion
}
