import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.AuthRepository
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.domain.SignInUseCase
import com.example.baubapchallenge.domain.SignInUseCaseImpl
import com.example.baubapchallenge.fakedata.ANY_PASSWORD
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.fakedata.ANY_USER_PHONE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Suppress("UnusedFlow")
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SignInUseCaseShould {

    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var signInUseCase: SignInUseCase

    @Before
    fun setUp() {
        signInUseCase = SignInUseCaseImpl(authRepository)
    }

    @Test
    fun `Get userId when findUserId is success`() = runTest {
        val resultSuccess = Result.success(ANY_USER_ID)
        whenever(authRepository.signIn(ANY_USER_PHONE, ANY_PASSWORD)).thenReturn(flowOf(resultSuccess))

        val result = signInUseCase(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(authRepository).signIn(ANY_USER_PHONE, ANY_PASSWORD)
        assertThatEquals(result?.getOrNull(), ANY_USER_ID)
    }

    @Test
    fun `Get SignInException when signIn is failure`() = runTest {
        val resultFailure = Result.failure<String>(AuthException.SignInException())
        whenever(authRepository.signIn(ANY_USER_PHONE, ANY_PASSWORD)).thenReturn(flowOf(resultFailure))

        val result = signInUseCase(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(authRepository).signIn(ANY_USER_PHONE, ANY_PASSWORD)
        assertThatIsInstanceOf<AuthException.SignInException>(result?.exceptionOrNull())
    }
}
