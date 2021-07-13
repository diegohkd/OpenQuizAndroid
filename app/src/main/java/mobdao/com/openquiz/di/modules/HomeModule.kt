package mobdao.com.openquiz.di.modules

import dagger.Module
import dagger.Provides
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepository
import mobdao.com.openquiz.data.repositories.opentriviarepository.OpenTriviaRepositoryImpl
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepositoryImpl
import mobdao.com.openquiz.data.server.mappers.QuestionServiceMapper
import mobdao.com.openquiz.data.utils.wrappers.firebaseauth.FirebaseAuth
import mobdao.com.openquiz.data.utils.wrappers.googleauthprovider.GoogleAuthProvider
import retrofit2.Retrofit

@Module
abstract class HomeModule {

    companion object {
        @Provides
        fun userAuthRepository(
            firebaseAuth: FirebaseAuth,
            googleAuthProvider: GoogleAuthProvider
        ): UserAuthRepository {
            return UserAuthRepositoryImpl(firebaseAuth, googleAuthProvider)
        }

        @Provides
        fun openTriviaRepository(
            retrofit: Retrofit,
            questionServiceMapper: QuestionServiceMapper
        ): OpenTriviaRepository {
            return OpenTriviaRepositoryImpl(retrofit, questionServiceMapper)
        }
    }
}
