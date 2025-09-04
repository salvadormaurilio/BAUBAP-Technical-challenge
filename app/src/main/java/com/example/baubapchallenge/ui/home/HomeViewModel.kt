package com.example.baubapchallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baubapchallenge.domain.GetUserDataUseCase
import com.example.baubapchallenge.domain.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getUserDataUseCase: GetUserDataUseCase) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun handleIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.GetUserData -> getUserData(intent.userId)
        }
    }

    private fun getUserData(userId: String) {
        viewModelScope.launch {
            getUserDataUseCase(userId)
                .onStart { updateSignInUiState(isLoading = true) }
                .onCompletion { updateSignInUiState(isLoading = false) }
                .collect { result ->
                    result.fold(
                        onSuccess = { userData -> updateSignInUiState(userData = userData) },
                        onFailure = { e -> updateSignInUiState(error = e) }
                    )
                }
        }
    }

    private fun updateSignInUiState(
        isLoading: Boolean? = null,
        userData: UserData? = null,
        error: Throwable? = null,
    ) {
        _homeUiState.update {
            it.copy(
                isLoading = isLoading ?: it.isLoading,
                userData = userData ?: it.userData,
                error = error
            )
        }
    }
}
