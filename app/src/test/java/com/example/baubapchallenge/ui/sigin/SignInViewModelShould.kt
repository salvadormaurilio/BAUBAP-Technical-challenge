package com.example.baubapchallenge.ui.sigin

import app.cash.turbine.test
import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.domain.SignInUseCase
import com.example.baubapchallenge.fakedata.ANY_CURP
import com.example.baubapchallenge.fakedata.ANY_INVALID_CURP
import com.example.baubapchallenge.fakedata.ANY_INVALID_PIN
import com.example.baubapchallenge.fakedata.ANY_PIN
import com.example.baubapchallenge.fakedata.ANY_USER_ID
import com.example.baubapchallenge.ui.signin.SignInUiEffect
import com.example.baubapchallenge.ui.signin.SignInUiIntent
import com.example.baubapchallenge.ui.signin.SignInViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("UnusedFlow")
@RunWith(MockitoJUnitRunner::class)
class SignInViewModelShould {

    @Mock
    private lateinit var signInUseCase: SignInUseCase

    private lateinit var signInViewModel: SignInViewModel

    @Before
    fun setup() {
        signInViewModel = SignInViewModel(signInUseCase)
    }

    @Test
    fun `Show Identifier error when identifier is invalid and avoid executing signIn`() = runTest {
        signInViewModel.handleIntent(SignInUiIntent.IdentifierChanged(ANY_INVALID_CURP))
        signInViewModel.handleIntent(SignInUiIntent.PinChanged(ANY_PIN))
        signInViewModel.handleIntent(SignInUiIntent.ConfirmSignIn)

        signInViewModel.signInUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.identifier, ANY_INVALID_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showIdentifierError, true)
            assertThatEquals(uiState.showPinError, false)
        }

        signInViewModel.signInUiEffect.test {
            expectNoEvents()
        }

        verify(signInUseCase, never()).invoke(any(), any())
    }

    @Test
    fun `Show Pin error when pin is invalid and avoid executing signIn`() = runTest {
        signInViewModel.handleIntent(SignInUiIntent.IdentifierChanged(ANY_CURP))
        signInViewModel.handleIntent(SignInUiIntent.PinChanged(ANY_INVALID_PIN))
        signInViewModel.handleIntent(SignInUiIntent.ConfirmSignIn)

        signInViewModel.signInUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.identifier, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_INVALID_PIN)
            assertThatEquals(uiState.showIdentifierError, false)
            assertThatEquals(uiState.showPinError, true)
        }

        signInViewModel.signInUiEffect.test {
            expectNoEvents()
        }

        verify(signInUseCase, never()).invoke(any(), any())
    }

    @Test
    fun `Send Success when fields are valid and signIn is success`() = runTest {
        val resultSuccess = Result.success(ANY_USER_ID)
        whenever(signInUseCase(ANY_CURP, ANY_PIN)).thenReturn(flowOf(resultSuccess))

        signInViewModel.handleIntent(SignInUiIntent.IdentifierChanged(ANY_CURP))
        signInViewModel.handleIntent(SignInUiIntent.PinChanged(ANY_PIN))

        signInViewModel.signInUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.identifier, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showIdentifierError, false)
            assertThatEquals(uiState.showPinError, false)

            signInViewModel.handleIntent(SignInUiIntent.ConfirmSignIn)

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            cancelAndIgnoreRemainingEvents()
        }
        signInViewModel.signInUiEffect.test {
            val uiEffect = awaitItem()
            assertThatEquals(uiEffect, SignInUiEffect.Success(ANY_USER_ID))
        }

        verify(signInUseCase).invoke(ANY_CURP, ANY_PIN)
    }

    @Test
    fun `Send Error when fields are valid and signIn is failure`() = runTest {
        val resultFailure = Result.failure<String>(AuthException.SignInException())
        whenever(signInUseCase(ANY_CURP, ANY_PIN)).thenReturn(flowOf(resultFailure))

        signInViewModel.handleIntent(SignInUiIntent.IdentifierChanged(ANY_CURP))
        signInViewModel.handleIntent(SignInUiIntent.PinChanged(ANY_PIN))

        signInViewModel.signInUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.identifier, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showIdentifierError, false)
            assertThatEquals(uiState.showPinError, false)
            assertThatEquals(uiState.isLoading, false)

            signInViewModel.handleIntent(SignInUiIntent.ConfirmSignIn)

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            cancelAndIgnoreRemainingEvents()
        }

        signInViewModel.signInUiEffect.test {
            val uiEffect = awaitItem()
            assertThatEquals(uiEffect, SignInUiEffect.ErrorOccurred(AuthException.SignInException()))
        }

        verify(signInUseCase).invoke(ANY_CURP, ANY_PIN)
    }
}