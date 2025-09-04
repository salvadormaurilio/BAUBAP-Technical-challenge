package com.example.baubapchallenge.ui.views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.baubapchallenge.R
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
        ),
        onClick = { if (!isLoading) onClick() }
    ) {
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp),
                color = MaterialTheme.colorScheme.surface
            )
        else
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleSmall
            )
    }
}

class IsLoadingParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(false, true)
}

@Preview(showBackground = true)
@Composable
fun ProgressButtonPreview(@PreviewParameter(IsLoadingParameterProvider::class) isLoading: Boolean) {
    BaubapChallengeTheme {
        ProgressButton(
            isLoading = isLoading,
            text = R.string.sign_in,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressButtonDisablePreview() {
    BaubapChallengeTheme {
        ProgressButton(
            enabled = false,
            text = R.string.sign_in,
            onClick = {}
        )
    }
}
