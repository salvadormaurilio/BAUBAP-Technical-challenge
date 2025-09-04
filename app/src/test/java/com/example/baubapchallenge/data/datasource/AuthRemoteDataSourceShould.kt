import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSource
import com.example.baubapchallenge.data.datasource.AuthRemoteDataSourceImpl
import com.example.baubapchallenge.data.datasource.firebase.FirestoreClient
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.fakedata.ANY_PASSWORD
import com.example.baubapchallenge.fakedata.ANY_PASSWORD_HASHED
import com.example.baubapchallenge.fakedata.ANY_USER_CURP
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.fakedata.ANY_USER_PHONE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AuthRemoteDataSourceShould {

    @Mock
    private lateinit var client: FirestoreClient

    private lateinit var authRemoteDataSource: AuthRemoteDataSource

    @Before
    fun setUp() {
        authRemoteDataSource = AuthRemoteDataSourceImpl(client)
    }

    @Test
    fun `Get UserAlreadyExistException when the user exist and signUp is success`() = runTest {
        whenever(client.userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)).thenReturn(true)

        val result = authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(client).userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)
        verify(client, never()).createUser(any(), any(), any())
        assertThatIsInstanceOf<AuthException.UserAlreadyExistException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get SignUpException when the user exist and signUp is failure`() = runTest {
        whenever(client.userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)).thenThrow(RuntimeException())

        val result = authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(client).userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)
        verify(client, never()).createUser(any(), any(), any())
        assertThatIsInstanceOf<AuthException.SignUpException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get userId when the user does not exist and signUp is success`() = runTest {
        whenever(client.userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)).thenReturn(false)
        whenever(client.createUser(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD_HASHED)).thenReturn(ANY_USER_ID)

        val result = authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(client).userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)
        verify(client).createUser(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD_HASHED)
        assertThatEquals(result?.getOrNull(), ANY_USER_ID)
    }

    @Test
    fun `Get SignUpException when the user does not exist and signUp is failure`() = runTest {
        whenever(client.userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)).thenReturn(false)
        whenever(client.createUser(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD_HASHED)).thenThrow(RuntimeException())

        val result = authRemoteDataSource.signUp(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD).firstOrNull()

        verify(client).userExistsByPhoneOrCurp(ANY_USER_PHONE, ANY_USER_CURP)
        verify(client).createUser(ANY_USER_PHONE, ANY_USER_CURP, ANY_PASSWORD_HASHED)
        assertThatIsInstanceOf<AuthException.SignUpException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get userId when signIn is success`() = runTest {
        whenever(client.findUserId(ANY_USER_PHONE, ANY_PASSWORD_HASHED)).thenReturn(ANY_USER_ID)

        val result = authRemoteDataSource.signIn(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(client).findUserId(ANY_USER_PHONE, ANY_PASSWORD_HASHED)
        assertThatEquals(result?.getOrNull(), ANY_USER_ID)
    }

    @Test
    fun `Get SignInException when signIn is failure`() = runTest {
        whenever(client.findUserId(ANY_USER_PHONE, ANY_PASSWORD_HASHED)).thenThrow(RuntimeException())

        val result = authRemoteDataSource.signIn(ANY_USER_PHONE, ANY_PASSWORD).firstOrNull()

        verify(client).findUserId(ANY_USER_PHONE, ANY_PASSWORD_HASHED)
        assertThatIsInstanceOf<AuthException.SignInException>(result?.exceptionOrNull())
    }
}
