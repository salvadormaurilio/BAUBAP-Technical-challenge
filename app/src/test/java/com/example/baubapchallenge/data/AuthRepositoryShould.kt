import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.AuthRepository
import com.example.baubapchallenge.data.AuthRepositoryImpl
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSource
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.fakedata.ANY_PASSWORD
import com.example.baubapchallenge.fakedata.ANY_USER_CURP
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
class AuthRepositoryShould {

    @Mock
    private lateinit var authRemoteDataSource: AuthRemoteDataSource

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(authRemoteDataSource)
    }

    @Test
    fun `Get userId when signUp is success`() = runTest {
        val resultSuccess = Result.success(ANY_USER_ID)
        whenever(authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD)).thenReturn(flowOf(resultSuccess))

        val result = authRepository.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(authRemoteDataSource).signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD)
        assertThatEquals(ANY_USER_ID, result?.getOrNull())
    }

    @Test
    fun `Get SignUpException when signUp is failure`() = runTest {
        val resultFailure = Result.failure<String>(AuthException.SignUpException())
        whenever(authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD)).thenReturn(flowOf(resultFailure))

        val result = authRepository.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(authRemoteDataSource).signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD)
        assertThatIsInstanceOf<AuthException.SignUpException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get userId when signIn is success`() = runTest {
        val resultSuccess = Result.success(ANY_USER_ID)
        whenever(authRemoteDataSource.signIn(ANY_USER_PHONE, ANY_PASSWORD)).thenReturn(flowOf(resultSuccess))

        val result = authRepository.signIn(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(authRemoteDataSource).signIn(ANY_USER_PHONE, ANY_PASSWORD)
        assertThatEquals(ANY_USER_ID, result?.getOrNull())
    }

    @Test
    fun `Get SignInException when signIn is failure`() = runTest {
        val resultFailure = Result.failure<String>(AuthException.SignInException())
        whenever(authRemoteDataSource.signIn(ANY_USER_PHONE, ANY_PASSWORD)).thenReturn(flowOf(resultFailure))

        val result = authRepository.signIn(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(authRemoteDataSource).signIn(ANY_USER_PHONE, ANY_PASSWORD)
        assertThatIsInstanceOf<AuthException.SignInException>(result?.exceptionOrNull())
    }
}
