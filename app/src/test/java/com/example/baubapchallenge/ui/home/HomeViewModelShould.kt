package com.example.baubapchallenge.ui.home

import app.cash.turbine.test
import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.core.assertThatIsInstanceOf
import com.example.baubapchallenge.data.exception.UserDataException
import com.example.baubapchallenge.domain.GetUserDataUseCase
import com.example.baubapchallenge.domain.models.UserData
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.fakedata.givenUserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelShould {

    @Mock
    private lateinit var getUserDataUseCase: GetUserDataUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getUserDataUseCase)
    }

    @Test
    fun `Get UserData when getUserDataUse is success`() = runTest {
        val userData = givenUserData()
        whenever(getUserDataUseCase(ANY_USER_ID)).thenReturn(flowOf(Result.success(userData)))

        viewModel.homeUiState.test {
            val uiStateInitial = awaitItem()
            assertThatEquals(uiStateInitial.isLoading, false)

            viewModel.handleIntent(HomeUiIntent.GetUserData(ANY_USER_ID))

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            val uiStateWithUserData = awaitItem()
            assertThatEquals(uiStateWithUserData.userData, givenUserData())

            cancelAndIgnoreRemainingEvents()

        }
        verify(getUserDataUseCase).invoke(ANY_USER_ID)
    }


    @Test
    fun `Get DataException when getUserData is failure`() = runTest {
        val resultFailure = Result.failure<UserData>(UserDataException.DataException())
        whenever(getUserDataUseCase(ANY_USER_ID)).thenReturn(flowOf(resultFailure))

        viewModel.homeUiState.test {
            val uiStateInitial = awaitItem()
            assertThatEquals(uiStateInitial.isLoading, false)

            viewModel.handleIntent(HomeUiIntent.GetUserData(ANY_USER_ID))

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            val uiStateWithError = awaitItem()
            assertThatIsInstanceOf<UserDataException.DataException>(uiStateWithError.error)

            cancelAndIgnoreRemainingEvents()
        }

        verify(getUserDataUseCase).invoke(ANY_USER_ID)
    }
}
