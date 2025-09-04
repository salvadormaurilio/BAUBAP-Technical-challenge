package com.example.baubapchallenge.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baubapchallenge.R
import com.example.baubapchallenge.data.exception.UserDataException
import com.example.baubapchallenge.domain.models.DepositAccount
import com.example.baubapchallenge.domain.models.UserData
import com.example.baubapchallenge.ui.theme.BaubapChallengeTheme
import com.example.baubapchallenge.ui.views.UserDataErrorScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    userId: String,
    onLoansHistory: () -> Unit = {}
) {
    val userData = viewModel.homeUiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.handleIntent(HomeUiIntent.GetUserData(userId))
    }

    HomeContent(
        isLoading = userData.value.isLoading,
        userData = userData.value.userData,
        error = userData.value.error,
        onRetry = { viewModel.handleIntent(HomeUiIntent.GetUserData(userId)) },
        onLoansHistory = onLoansHistory
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    isLoading: Boolean = false,
    userData: UserData? = null,
    error: Throwable? = null,
    onRetry: () -> Unit = {},
    onLoansHistory: () -> Unit = {}
) {
    Scaffold(
        topBar = { HomeAppBar() }
    ) { paddingValues ->

        LoadingContainer(isLoading)

        HomeContainer(
            modifier = Modifier.padding(paddingValues),
            userData = userData,
            onLoansHistory = onLoansHistory
        )

        UserDataErrorScreen(
            modifier = Modifier.padding(paddingValues),
            error = error,
            onRetry = onRetry
        )
    }
}

@Composable
private fun LoadingContainer(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun HomeContainer(
    modifier: Modifier = Modifier,
    userData: UserData?,
    onLoansHistory: () -> Unit
) {
    if (userData == null) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        EditableTextField(
            label = stringResource(R.string.cellphone_number),
            value = userData.phone
        )

        EditableTextField(
            label = stringResource(R.string.email),
            value = userData.email
        )

        InfoBlock(
            title = stringResource(R.string.full_name),
            value = userData.name
        )
        InfoBlock(
            title = stringResource(R.string.curp_label),
            value = userData.curp
        )
        InfoBlock(
            title = stringResource(R.string.address),
            value = userData.address
        )

        HorizontalDivider()

        Text(
            text = stringResource(R.string.deposit_accounts_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = stringResource(R.string.deposit_accounts_hint),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        userData.depositAccounts.forEach { acc ->
            DepositAccountCard(
                account = acc
            )
        }

        Button(
            onClick = onLoansHistory,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = stringResource(R.string.loans_history),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeAppBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.AccountBalanceWallet,
                    contentDescription = stringResource(R.string.personal_data_title),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.personal_data_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun EditableTextField(
    label: String,
    value: String
) {
    var editable by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(editable) {
        if (editable) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus(force = true)
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .focusProperties { canFocus = editable },
        value = value,
        readOnly = !editable,
        onValueChange = {},
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { editable = !editable }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
private fun InfoBlock(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun DepositAccountCard(
    account: DepositAccount
) {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(12.dp),
                    imageVector = Icons.Filled.Wallet,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = account.bankName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = account.type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = stringResource(R.string.clabe, account.clabe),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLoadingPreview() {
    BaubapChallengeTheme {
        HomeContent(
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    BaubapChallengeTheme {
        HomeContent(
            userData = UserData(
                phone = "+5212345678",
                email = "correo@baubap.com",
                name = "Juan Perez",
                curp = "AELM930630HDFLLG01",
                address = "Popayan Caunca",
                depositAccounts = listOf(
                    DepositAccount("Afirme", "Tarjeta débito", "1327639849280480"),
                    DepositAccount("BBVA BANCOMER", "Tarjeta débito", "1327639849280480"),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenWithErrorPreview() {
    BaubapChallengeTheme {
        HomeContent(
            error = UserDataException.DataException()
        )
    }
}
