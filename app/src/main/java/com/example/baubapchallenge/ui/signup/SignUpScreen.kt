package com.example.baubapchallenge.ui.signup

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baubapchallenge.R
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onBack: () -> Unit = {},
    onSignUp: (String, String) -> Unit = { _, _ -> },
    onConsultCurp: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            SignUpTopAppBar(onBack)
        }
    ) { paddingValues ->
        SignUpContainer(
            modifier = Modifier.padding(paddingValues),
            onSignUp = onSignUp,
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
    onSignUp: (String, String) -> Unit,
    onConsultCurp: () -> Unit,
    onSignIn: () -> Unit
) {
    var phone by rememberSaveable { mutableStateOf("") }
    var curp by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }

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
            onValueChange = { phone = it },
            label = {
                Text(
                    text = stringResource(R.string.phone_number_10_digits),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = curp,
            onValueChange = { curp = it },
            label = {
                Text(
                    text = stringResource(R.string.curp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pin,
            onValueChange = { pin = it },
            label = {
                Text(
                    text = stringResource(R.string.enter_pin),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
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
            enabled = phone.length == 10 && curp.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
            ),
            onClick = { onSignUp(phone, curp) }
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
fun SignUpScreenPreview() {
    BaubapChallengeTheme {
        SignUpScreen()
    }
}
