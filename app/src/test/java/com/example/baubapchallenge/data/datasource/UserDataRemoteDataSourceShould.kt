import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSource
import com.example.baubapchallenge.data.datasource.UserDataRemoteDataSourceImpl
import com.example.baubapchallenge.data.datasource.firebase.FirestoreClient
import com.example.baubapchallenge.data.exception.UserDataException
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.fakedata.givenUserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserDataRemoteDataSourceShould {

    @Mock
    private lateinit var client: FirestoreClient

    private lateinit var userDataRemoteDataSource: UserDataRemoteDataSource

    @Before
    fun setUp() {
        userDataRemoteDataSource = UserDataRemoteDataSourceImpl(client)
    }

    @Test
    fun `Get UserData when getUserData is success`() = runTest {
        val userdata = givenUserData()
        whenever(client.getUserData(ANY_USER_ID)).thenReturn(userdata)

        val result = userDataRemoteDataSource.getUserData(ANY_USER_ID).firstOrNull()

        verify(client).getUserData(ANY_USER_ID)
        assertThatEquals(userdata, result?.getOrNull())
    }

    @Test
    fun `Get DataException when getUserData is failure`() = runTest {
        whenever(client.getUserData(ANY_USER_ID)).thenThrow(RuntimeException())

        val result = userDataRemoteDataSource.getUserData(ANY_USER_ID).firstOrNull()

        verify(client).getUserData(ANY_USER_ID)
        assertThatIsInstanceOf<UserDataException.DataException>(result?.exceptionOrNull())
    }
}
