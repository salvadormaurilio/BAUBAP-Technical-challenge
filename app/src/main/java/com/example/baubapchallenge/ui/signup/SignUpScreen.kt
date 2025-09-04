package com.example.baubapchallenge.ui.signup

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baubapchallenge.R
import com.example.baubapchallenge.data.exception.AuthException
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onConsultCurp: () -> Unit,
    onSignIn: () -> Unit,
    onHome: () -> Unit,
    onBack: () -> Unit
) {
    val uiState = viewModel.signUpUiState.collectAsState()
    val uiEffect = viewModel.signUpUiEffect

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {
                is SignUpUiEffect.Success -> onHome()
                is SignUpUiEffect.Message -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(getMessageErrorFromAuthException(effect.error))
                    )
                }
            }
        }
    }

    SignUpContent(
        phone = uiState.value.phone,
        curp = uiState.value.curp,
        pin = uiState.value.pin,
        showPhoneError = uiState.value.showPhoneError,
        showCurpError = uiState.value.showCurpError,
        showPinError = uiState.value.showPinError,
        snackbarHostState = snackbarHostState,
        onPhoneChanged = { viewModel.handleIntent(SignUpUiIntent.PhoneChanged(it)) },
        onCurpChanged = { viewModel.handleIntent(SignUpUiIntent.CurpChanged(it)) },
        onPinChanged = { viewModel.handleIntent(SignUpUiIntent.PinChanged(it)) },
        onConfirmSignUp = { viewModel.handleIntent(SignUpUiIntent.ConfirmSignUp) },
        onConsultCurp = onConsultCurp,
        onSignIn = onSignIn,
        onBack = onBack
    )
}

private fun getMessageErrorFromAuthException(error: Throwable) = when (error) {
    is AuthException.UserAlreadyExistException -> R.string.error_user_exists
    is AuthException.SignUpException -> R.string.error_signup_generic
    is AuthException.NetworkException -> R.string.error_network
    else -> R.string.error_unknown
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpContent(
    phone: String,
    curp: String,
    pin: String,
    showPhoneError: Boolean = false,
    showCurpError: Boolean = false,
    showPinError: Boolean = false,
    snackbarHostState: SnackbarHostState? = null,
    onPhoneChanged: (String) -> Unit = {},
    onCurpChanged: (String) -> Unit = {},
    onPinChanged: (String) -> Unit = {},
    onConfirmSignUp: () -> Unit = {},
    onConsultCurp: () -> Unit = {},
    onSignIn: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = { SignUpTopAppBar(onBack) },
        snackbarHost = {
            snackbarHostState?.let {
                SnackbarHost(it) { data -> Snackbar(snackbarData = data) }
            }
        }

    ) { paddingValues ->
        SignUpContainer(
            modifier = Modifier.padding(paddingValues),
            phone = phone,
            curp = curp,
            pin = pin,
            showPhoneError = showPhoneError,
            showCurpError = showCurpError,
            showPinError = showPinError,
            onPhoneChanged = onPhoneChanged,
            onCurpChanged = onCurpChanged,
            onPinChanged = onPinChanged,
            onConfirmSignUp = onConfirmSignUp,
            onConsultCurp = onConsultCurp,
            onSignIn = onSignIn
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SignUpTopAppBar(onBack: () -> Unit) {
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
private fun SignUpContainer(
    modifier: Modifier = Modifier,
    phone: String,
    curp: String,
    pin: String,
    showPhoneError: Boolean,
    showCurpError: Boolean,
    showPinError: Boolean,
    onPhoneChanged: (String) -> Unit,
    onCurpChanged: (String) -> Unit,
    onPinChanged: (String) -> Unit,
    onConfirmSignUp: () -> Unit,
    onConsultCurp: () -> Unit,
    onSignIn: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phone,
            onValueChange = { onPhoneChanged(it) },
            label = {
                Text(
                    text = stringResource(R.string.phone_number_10_digits),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
            isError = showPhoneError,
            supportingText = {
                if (showPhoneError) {
                    Text(
                        text = stringResource(R.string.error_phone),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = curp,
            onValueChange = { onCurpChanged(it) },
            label = {
                Text(
                    text = stringResource(R.string.curp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
            isError = showCurpError,
            supportingText = {
                if (showCurpError) {
                    Text(
                        text = stringResource(R.string.error_curp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pin,
            onValueChange = { onPinChanged(it) },
            label = {
                Text(
                    text = stringResource(R.string.enter_pin),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = showPinError,
            supportingText = {
                if (showPinError) {
                    Text(
                        text = stringResource(R.string.error_pin),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.no_remember_curp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.width(4.dp))

            Text(
                modifier = Modifier.clickable(onClick = onConsultCurp),
                text = stringResource(R.string.consult_here),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = phone.isNotBlank() && curp.isNotBlank() && pin.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
            ),
            onClick = { onConfirmSignUp() }
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.width(4.dp))

            Text(
                modifier = Modifier.clickable(onClick = onSignIn),
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpContentPreview() {
    BaubapChallengeTheme {
        var phone by rememberSaveable { mutableStateOf("") }
        var curp by rememberSaveable { mutableStateOf("") }
        var pin by rememberSaveable { mutableStateOf("") }
        SignUpContent(
            phone = phone,
            curp = curp,
            pin = pin,
            onPhoneChanged = { phone = it },
            onCurpChanged = { curp = it },
            onPinChanged = { pin = it }
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Preview(showBackground = true)
@Composable
fun SignUpContentWithErrorsPreview() {
    BaubapChallengeTheme {
        var phone by rememberSaveable { mutableStateOf("") }
        var curp by rememberSaveable { mutableStateOf("") }
        var pin by rememberSaveable { mutableStateOf("") }
        var showPhoneError by rememberSaveable { mutableStateOf(true) }
        var showCurpError by rememberSaveable { mutableStateOf(true) }
        var showPinError by rememberSaveable { mutableStateOf(true) }
        val snackbarHostState = remember { SnackbarHostState() }
        val nerWorkMessage = stringResource(R.string.error_network)

        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message = nerWorkMessage)
        }

        SignUpContent(
            phone = phone,
            curp = curp,
            pin = pin,
            showPhoneError = showPhoneError,
            showCurpError = showCurpError,
            showPinError = showPinError,
            snackbarHostState = snackbarHostState,
            onPhoneChanged = {
                phone = it
                showPhoneError = false
            },
            onCurpChanged = {
                curp = it
                showCurpError = false
            },
            onPinChanged = {
                pin = it
                showPinError = false
            }
        )
    }
}

