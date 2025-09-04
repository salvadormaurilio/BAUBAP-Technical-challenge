import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.UserDataRepository
import com.example.baubapchallenge.data.UserDataRepositoryImpl
import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSource
import com.example.baubapchallenge.data.exception.UserDataException
import com.example.baubapchallenge.domain.models.UserData
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.fakedata.givenUserData
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
class UserDataRepositoryShould {

    @Mock
    private lateinit var userDataRemoteDataSource: UserDataRemoteDataSource

    private lateinit var userDataRepository: UserDataRepository

    @Before
    fun setUp() {
        userDataRepository = UserDataRepositoryImpl(userDataRemoteDataSource)
    }

    @Test
    fun `Get UserData when getUserData is success`() = runTest {
        val userdata = givenUserData()
        val resultSuccess = Result.success(userdata)
        whenever(userDataRemoteDataSource.getUserData(ANY_USER_ID)).thenReturn(flowOf(resultSuccess))

        val result = userDataRepository.getUserData(ANY_USER_ID).firstOrNull()

        verify(userDataRemoteDataSource).getUserData(ANY_USER_ID)
        assertThatEquals(userdata, result?.getOrNull())
    }

    @Test
    fun `Get DataException when getUserData is failure`() = runTest {
        val resultFailure = Result.failure<UserData>(UserDataException.DataException())
        whenever(userDataRemoteDataSource.getUserData(ANY_USER_ID)).thenReturn(flowOf(resultFailure))

        val result = userDataRepository.getUserData(ANY_USER_ID).firstOrNull()

        verify(userDataRemoteDataSource).getUserData(ANY_USER_ID)
        assertThatIsInstanceOf<UserDataException.DataException>(result?.exceptionOrNull())
    }
}
