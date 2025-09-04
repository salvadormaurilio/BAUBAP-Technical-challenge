package com.example.baubapchallenge.ui.signup

import app.cash.turbine.test
import com.example.baubapchallenge.core.assertThatEquals
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.domain.SignUpUseCase
import com.example.baubapchallenge.fakedata.ANY_CURP
import com.example.baubapchallenge.fakedata.ANY_INVALID_CURP
import com.example.baubapchallenge.fakedata.ANY_INVALID_PHONE
import com.example.baubapchallenge.fakedata.ANY_INVALID_PIN
import com.example.baubapchallenge.fakedata.ANY_PHONE
import com.example.baubapchallenge.fakedata.ANY_PIN
import com.example.baubapchallenge.fakedata.ANY_USER_ID
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
class SignUpViewModelShould {

    @Mock
    private lateinit var signUpUseCase: SignUpUseCase

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setup() {
        signUpViewModel = SignUpViewModel(signUpUseCase)
    }

    @Test
    fun `Show Phone error when phone is invalid and avoid executing signUp`() = runTest {
        signUpViewModel.handleIntent(SignUpUiIntent.PhoneChanged(ANY_INVALID_PHONE))
        signUpViewModel.handleIntent(SignUpUiIntent.CurpChanged(ANY_CURP))
        signUpViewModel.handleIntent(SignUpUiIntent.PinChanged(ANY_PIN))
        signUpViewModel.handleIntent(SignUpUiIntent.ConfirmSignUp)

        signUpViewModel.signUpUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.phone, ANY_INVALID_PHONE)
            assertThatEquals(uiState.curp, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showPhoneError, true)
            assertThatEquals(uiState.showCurpError, false)
            assertThatEquals(uiState.showPinError, false)
        }

        signUpViewModel.signUpUiEffect.test {
            expectNoEvents()
        }

        verify(signUpUseCase, never()).invoke(any(), any(), any())
    }

    @Test
    fun `Show CURP error when curp is invalid and avoid executing signUp`() = runTest {
        signUpViewModel.handleIntent(SignUpUiIntent.PhoneChanged(ANY_PHONE))
        signUpViewModel.handleIntent(SignUpUiIntent.CurpChanged(ANY_INVALID_CURP))
        signUpViewModel.handleIntent(SignUpUiIntent.PinChanged(ANY_PIN))
        signUpViewModel.handleIntent(SignUpUiIntent.ConfirmSignUp)

        signUpViewModel.signUpUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.phone, ANY_PHONE)
            assertThatEquals(uiState.curp, ANY_INVALID_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showPhoneError, false)
            assertThatEquals(uiState.showCurpError, true)
            assertThatEquals(uiState.showPinError, false)
        }

        signUpViewModel.signUpUiEffect.test {
            expectNoEvents()
        }

        verify(signUpUseCase, never()).invoke(any(), any(), any())
    }

    @Test
    fun `Show Pin error when pin is invalid and avoid executing signUp`() = runTest {
        signUpViewModel.handleIntent(SignUpUiIntent.PhoneChanged(ANY_PHONE))
        signUpViewModel.handleIntent(SignUpUiIntent.CurpChanged(ANY_CURP))
        signUpViewModel.handleIntent(SignUpUiIntent.PinChanged(ANY_INVALID_PIN))
        signUpViewModel.handleIntent(SignUpUiIntent.ConfirmSignUp)

        signUpViewModel.signUpUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.phone, ANY_PHONE)
            assertThatEquals(uiState.curp, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_INVALID_PIN)
            assertThatEquals(uiState.showPhoneError, false)
            assertThatEquals(uiState.showCurpError, false)
            assertThatEquals(uiState.showPinError, true)
        }

        signUpViewModel.signUpUiEffect.test {
            expectNoEvents()
        }

        verify(signUpUseCase, never()).invoke(any(), any(), any())
    }

    @Test
    fun `Send Success when fields are valid and signUp is success`() = runTest {
        val resultSuccess = Result.success(ANY_USER_ID)
        whenever(signUpUseCase(ANY_PHONE, ANY_CURP, ANY_PIN)).thenReturn(flowOf(resultSuccess))

        signUpViewModel.handleIntent(SignUpUiIntent.PhoneChanged(ANY_PHONE))
        signUpViewModel.handleIntent(SignUpUiIntent.CurpChanged(ANY_CURP))
        signUpViewModel.handleIntent(SignUpUiIntent.PinChanged(ANY_PIN))

        signUpViewModel.signUpUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.phone, ANY_PHONE)
            assertThatEquals(uiState.curp, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showPhoneError, false)
            assertThatEquals(uiState.showCurpError, false)
            assertThatEquals(uiState.showPinError, false)

            signUpViewModel.handleIntent(SignUpUiIntent.ConfirmSignUp)

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            cancelAndIgnoreRemainingEvents()
        }

        signUpViewModel.signUpUiEffect.test {
            val uiEffect = awaitItem()
            assertThatEquals(uiEffect, SignUpUiEffect.Success(ANY_USER_ID))
        }

        verify(signUpUseCase).invoke(ANY_PHONE, ANY_CURP, ANY_PIN)
    }

    @Test
    fun `Send Error when fields are valid and signUp is failure`() = runTest {
        val resultFailure = Result.failure<String>(AuthException.SignUpException())
        whenever(signUpUseCase(ANY_PHONE, ANY_CURP, ANY_PIN)).thenReturn(flowOf(resultFailure))

        signUpViewModel.handleIntent(SignUpUiIntent.PhoneChanged(ANY_PHONE))
        signUpViewModel.handleIntent(SignUpUiIntent.CurpChanged(ANY_CURP))
        signUpViewModel.handleIntent(SignUpUiIntent.PinChanged(ANY_PIN))

        signUpViewModel.signUpUiState.test {
            val uiState = awaitItem()
            assertThatEquals(uiState.phone, ANY_PHONE)
            assertThatEquals(uiState.curp, ANY_CURP)
            assertThatEquals(uiState.pin, ANY_PIN)
            assertThatEquals(uiState.showPhoneError, false)
            assertThatEquals(uiState.showCurpError, false)
            assertThatEquals(uiState.showPinError, false)

            signUpViewModel.handleIntent(SignUpUiIntent.ConfirmSignUp)

            val uiStateStart = awaitItem()
            assertThatEquals(uiStateStart.isLoading, true)

            cancelAndIgnoreRemainingEvents()
        }

        signUpViewModel.signUpUiEffect.test {
            val uiEffect = awaitItem()
            assertThatEquals(uiEffect, SignUpUiEffect.ErrorOccurred(AuthException.SignUpException()))
        }

        verify(signUpUseCase).invoke(ANY_PHONE, ANY_CURP, ANY_PIN)
    }
}
