package com.example.baubapchallenge.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baubapchallenge.core.extensions.isValidCurp
import com.example.baubapchallenge.core.extensions.isValidPhone
import com.example.baubapchallenge.core.extensions.isValidPin
import com.example.baubapchallenge.domain.SignInUseCase
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
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    private val _signInUiEffect = Channel<SignInUiEffect>(capacity = Channel.BUFFERED)
    val signInUiEffect = _signInUiEffect.receiveAsFlow()

    fun handleIntent(intent: SignInUiIntent) {
        when (intent) {
            is SignInUiIntent.IdentifierChanged -> updateSignInUiState(identifier = intent.value, showIdentifierError = false)
            is SignInUiIntent.PinChanged -> updateSignInUiState(pin = intent.value, showPinError = false)
            is SignInUiIntent.ConfirmSignIn -> confirmSignIn()
        }
    }

    private fun confirmSignIn() {
        if (!areInputsValid()) return

        viewModelScope.launch {
            val uiState = _signInUiState.value
            signInUseCase(uiState.identifier, uiState.pin)
                .onStart { updateSignInUiState(isLoading = true) }
                .onCompletion { updateSignInUiState(isLoading = false) }
                .collect { result ->
                    result.fold(
                        onSuccess = { id -> _signInUiEffect.send(SignInUiEffect.Success(id)) },
                        onFailure = { e -> _signInUiEffect.send(SignInUiEffect.ErrorOccurred(e)) }
                    )
                }
        }
    }

    private fun areInputsValid(): Boolean {
        val state = _signInUiState.value
        val idValid = state.identifier.isValidPhone() || state.identifier.isValidCurp()
        val pinValid = state.pin.isValidPin()

        updateSignInUiState(
            showIdentifierError = !idValid,
            showPinError = !pinValid
        )

        return idValid && pinValid
    }

    private fun updateSignInUiState(
        identifier: String? = null,
        pin: String? = null,
        showIdentifierError: Boolean? = null,
        showPinError: Boolean? = null,
        isLoading: Boolean? = null
    ) {
        _signInUiState.update {
            it.copy(
                identifier = identifier ?: it.identifier,
                pin = pin ?: it.pin,
                showIdentifierError = showIdentifierError ?: it.showIdentifierError,
                showPinError = showPinError ?: it.showPinError,
                isLoading = isLoading ?: it.isLoading
            )
        }
    }
}
