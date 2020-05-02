package mobdao.com.openquiz

import android.app.Application
import mobdao.com.openquiz.data.di.*
import mobdao.com.openquiz.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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