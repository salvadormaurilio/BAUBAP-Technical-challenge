package com.example.baubapchallenge.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baubapchallenge.core.extensions.isValidCurp
import com.example.baubapchallenge.core.extensions.isValidPhone
import com.example.baubapchallenge.core.extensions.isValidPin
import com.example.baubapchallenge.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUp: SignUpUseCase) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _signUpUiEffect = Channel<SignUpUiEffect>()
    val signUpUiEffect = _signUpUiEffect.receiveAsFlow()

    fun handleIntent(intent: SignUpUiIntent) {
        when (intent) {
            is SignUpUiIntent.PhoneChanged -> updateSingUpUiState(phone = intent.value, showPhoneError = false)
            is SignUpUiIntent.CurpChanged -> updateSingUpUiState(curp = intent.value, showCurpError = false)
            is SignUpUiIntent.PinChanged -> updateSingUpUiState(pin = intent.value, showPinError = false)
            is SignUpUiIntent.ConfirmSignUp -> confirmSingUp()
        }
    }

    private fun confirmSingUp() {
        if (!areInputsValid()) return

        viewModelScope.launch {
            val uiState = _signUpUiState.value
            signUp(uiState.phone, uiState.curp, uiState.pin)
                .onStart { updateSingUpUiState(isLoading = true) }
                .onCompletion { updateSingUpUiState(isLoading = false) }
                .collect { result ->
                    result.fold(
                        onSuccess = { id ->
                            _signUpUiEffect.send(SignUpUiEffect.Success(id))
                        },
                        onFailure = { e ->
                            _signUpUiEffect.send(SignUpUiEffect.Message(e))
                        }
                    )
                }

        }
    }

    private fun areInputsValid(): Boolean {
        val uiState = _signUpUiState.value
        val isPhoneValid = uiState.phone.isValidPhone()
        val isCurpValid = uiState.curp.isValidCurp()
        val isPinValid = uiState.pin.isValidPin()

        updateSingUpUiState(
            showPhoneError = !isPhoneValid,
            showCurpError = !isCurpValid,
            showPinError = !isPinValid
        )

        return isPhoneValid && isCurpValid && !isPinValid
    }

    private fun updateSingUpUiState(
        phone: String? = null,
        curp: String? = null,
        pin: String? = null,
        showPhoneError: Boolean? = null,
        showCurpError: Boolean? = null,
        showPinError: Boolean? = null,
        isLoading: Boolean? = null
    ) {
        _signUpUiState.update {
            it.copy(
                phone = phone ?: it.phone,
                curp = curp ?: it.curp,
                pin = pin ?: it.pin,
                showPhoneError = showPhoneError ?: it.showPhoneError,
                showCurpError = showCurpError ?: it.showCurpError,
                showPinError = showPinError ?: it.showPinError,
                isLoading = isLoading ?: it.isLoading
            )
        }
    }
}
