package mobdao.com.openquiz.modules.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import mobdao.com.openquiz.data.repositories.userauthrepository.UserAuthRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var userAuthRepository: UserAuthRepository

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `Return true if user is not logged in`() {
        val liveData = MutableLiveData<Boolean>().apply { value = true }
        every { userAuthRepository.isUserLoggedIn() }.returns(liveData)
        splashViewModel = SplashViewModel(userAuthRepository)

        val result = splashViewModel.isUserLoggedInLiveData

        assertEquals(true, result.value)
    }

    @Test
    fun `Return false if user is not logged in`() {
        val liveData = MutableLiveData<Boolean>().apply { value = false }
        every { userAuthRepository.isUserLoggedIn() }.returns(liveData)
        splashViewModel = SplashViewModel(userAuthRepository)

        val result = splashViewModel.isUserLoggedInLiveData

        assertNotEquals(true, result.value)
    }
}
