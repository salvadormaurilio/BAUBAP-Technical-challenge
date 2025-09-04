package com.example.baubapchallenge.ui.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baubapchallenge.R
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme
import com.example.baubapchallenge.ui.views.AuthTextField
import com.example.baubapchallenge.ui.views.ProgressButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onForgotPin: () -> Unit = {},
    onSingUp: () -> Unit = {},
    onHome: (String) -> Unit = {}
) {
    val uiState = viewModel.signInUiState.collectAsState()
    val uiEffect = viewModel.signInUiEffect

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {
                is SignInUiEffect.Success -> onHome(effect.userId)
                is SignInUiEffect.ErrorOccurred -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(getMessageErrorFromAuthException(effect.error))
                    )
                }
            }
        }
    }

    SignInContent(
        identifier = uiState.value.identifier,
        pin = uiState.value.pin,
        showIdentifierError = uiState.value.showIdentifierError,
        showPinError = uiState.value.showPinError,
        isLoading = uiState.value.isLoading,
        snackbarHostState = snackbarHostState,
        onIdentifierChanged = { viewModel.handleIntent(SignInUiIntent.IdentifierChanged(it)) },
        onPinChanged = { viewModel.handleIntent(SignInUiIntent.PinChanged(it)) },
        onConfirmSignIn = { viewModel.handleIntent(SignInUiIntent.ConfirmSignIn) },
        onForgotPin = onForgotPin,
        onSingUp = onSingUp,
        onBack = onBack
    )
}

private fun getMessageErrorFromAuthException(error: Throwable) = when (error) {
    is AuthException.SignInException -> R.string.error_signin_generic
    is AuthException.NetworkException -> R.string.error_network
    else -> R.string.error_unknown
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInContent(
    identifier: String,
    pin: String,
    showIdentifierError: Boolean = false,
    showPinError: Boolean = false,
    isLoading: Boolean = false,
    snackbarHostState: SnackbarHostState? = null,
    onIdentifierChanged: (String) -> Unit = {},
    onPinChanged: (String) -> Unit = {},
    onConfirmSignIn: () -> Unit = {},
    onForgotPin: () -> Unit = {},
    onSingUp: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = { SingInTopAppBar(onBack) },
        snackbarHost = {
            snackbarHostState?.let {
                SnackbarHost(it) { data -> Snackbar(snackbarData = data) }
            }
        }
    ) { paddingValues ->
        SignInContainer(
            modifier = Modifier.padding(paddingValues),
            identifier = identifier,
            pin = pin,
            showIdentifierError = showIdentifierError,
            showPinError = showPinError,
            isLoading = isLoading,
            onIdentifierChanged = onIdentifierChanged,
            onPinChanged = onPinChanged,
            onConfirmSignIn = onConfirmSignIn,
            onSingUp = onSingUp,
            onForgotPin = onForgotPin,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SingInTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
private fun SignInContainer(
    modifier: Modifier = Modifier,
    identifier: String,
    pin: String,
    showIdentifierError: Boolean,
    showPinError: Boolean,
    isLoading: Boolean,
    onIdentifierChanged: (String) -> Unit,
    onPinChanged: (String) -> Unit,
    onConfirmSignIn: () -> Unit,
    onForgotPin: () -> Unit,
    onSingUp: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.height(24.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            value = identifier,
            label = stringResource(R.string.enter_curp_or_phone),
            showError = showIdentifierError,
            error = stringResource(R.string.error_identifier),
            onValueChange = { if (!isLoading) onIdentifierChanged(it) }
        )

        Spacer(Modifier.height(16.dp))

        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pin,
            label = stringResource(R.string.enter_pin),
            showError = showPinError,
            error = stringResource(R.string.error_pin),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            onValueChange = { if (!isLoading) onPinChanged(it) }
        )

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.forgot_pin),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.width(4.dp))

            Text(
                modifier = Modifier.clickable(onClick = onForgotPin),
                text = stringResource(R.string.recover_here),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary

            )
        }

        Spacer(Modifier.weight(1f))

        ProgressButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = identifier.isNotBlank() && pin.isNotBlank(),
            isLoading = isLoading,
            text = R.string.sign_in,
            onClick = onConfirmSignIn
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.width(4.dp))

            Text(
                modifier = Modifier.clickable(onClick = onSingUp),
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInContentPreview() {
    BaubapChallengeTheme {
        var identifier by rememberSaveable { mutableStateOf("") }
        var pin by rememberSaveable { mutableStateOf("") }
        SignInContent(
            identifier = identifier,
            pin = pin,
            onIdentifierChanged = { identifier = it },
            onPinChanged = { pin = it }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SignInContentLoadingPreview() {
    BaubapChallengeTheme {
        var identifier by rememberSaveable { mutableStateOf("AELM930630HDFLLG01") }
        var pin by rememberSaveable { mutableStateOf("123443") }
        SignInContent(
            identifier = identifier,
            pin = pin,
            isLoading = true,
            onIdentifierChanged = { identifier = it },
            onPinChanged = { pin = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInContainerWithErrorsPreview() {
    BaubapChallengeTheme {
        var identifier by rememberSaveable { mutableStateOf("") }
        var pin by rememberSaveable { mutableStateOf("") }
        val snackbarHostState = remember { SnackbarHostState() }
        var showIdentifierError by rememberSaveable { mutableStateOf(true) }
        var showPinError by rememberSaveable { mutableStateOf(true) }
        val nerWorkMessage = stringResource(R.string.error_network)

        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message = nerWorkMessage)
        }

        SignInContent(
            identifier = identifier,
            pin = pin,
            showIdentifierError = showIdentifierError,
            showPinError = showPinError,
            snackbarHostState = snackbarHostState,
            onIdentifierChanged = {
                identifier = it
                showIdentifierError = false
            },
            onPinChanged = {
                pin = it
                showPinError = false
            }
        )
    }
}
