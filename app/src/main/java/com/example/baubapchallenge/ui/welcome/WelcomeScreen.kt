package com.example.baubapchallenge.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baubapchallenge.R
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme

@Composable
fun WelcomeScreen(
    onSignUp: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.logo_welcome),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.welcome),
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.reach_financial_goals_today),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onSignUp,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onSignIn,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenScreenPreview() {
    BaubapChallengeTheme {
        WelcomeScreen()
    }
}
