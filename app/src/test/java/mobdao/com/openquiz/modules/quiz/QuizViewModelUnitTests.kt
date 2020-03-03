package mobdao.com.openquiz.modules.quiz

import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test

class QuizViewModelUnitTests {

    private lateinit var quizViewModel: QuizViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        quizViewModel = QuizViewModel()
    }

    @Test
    fun `Show questions on init`() {

    }

//    @Test
//    fun `Initialize answers with same size as question on init`() {
//
//    }
//
//    @Test
//    fun `Initialize answers with same size as question on init`() {
//
//    }
}