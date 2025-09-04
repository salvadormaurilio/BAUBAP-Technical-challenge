package com.example.baubapchallenge.ui.views

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.baubapchallenge.R
import com.example.baubapchallenge.core.extensions.empty
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = String.empty(),
    showError: Boolean = false,
    error: String = String.empty(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        singleLine = true,
        isError = showError,
        supportingText = {
            if (showError) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = keyboardOptions
    )
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldPreview() {
    BaubapChallengeTheme {
        var phone by rememberSaveable { mutableStateOf("") }
        AuthTextField(
            value = phone,
            label = stringResource(R.string.phone_number_10_digits),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { phone = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldWithErrorPreview() {
    BaubapChallengeTheme {
        var phone by rememberSaveable { mutableStateOf("45456") }
        AuthTextField(
            value = phone,
            label = stringResource(R.string.phone_number_10_digits),
            showError = true,
            error = stringResource(R.string.error_phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { phone = it }
        )
    }
}