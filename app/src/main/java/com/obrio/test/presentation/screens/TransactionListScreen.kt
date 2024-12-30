package com.obrio.test.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.obrio.test.R
import com.obrio.test.domain.model.Transaction
import com.obrio.test.presentation.components.PositiveNumberInputField
import com.obrio.test.presentation.model.TransactionUiModel
import com.obrio.test.presentation.viewmodels.BalanceViewModel
import com.obrio.test.presentation.viewmodels.BitcoinPriceViewModel
import com.obrio.test.presentation.viewmodels.TransactionListViewModel

/**
 * Composable for the Transaction List Screen.
 * Displays the user's Bitcoin balance, a list of transactions (time, bitcoin amount, category)
 * grouped by day, and buttons to add a new transaction and top up the balance.
 * Displays the Bitcoin-to-USD exchange rate in the top-right corner.
 *
 * Features:
 * - Bitcoin-to-USD exchange rate updates every time (no more than once an hour).
 * - Supports paginated loading of transactions (20 transaction).
 * - Navigation to the "Add Transaction" screen.
 * - Top up the balance button has to open Pop-up with Number input field for money.
 *
 * * @param viewModel The [BitcoinPriceViewModel] which is injected using Hilt
 */

@Composable
fun TransactionListScreen(
    onNavigateToAddTransactionScreen: () -> Unit,
    viewModel: BalanceViewModel = hiltViewModel()
) {
    var showPopup by rememberSaveable { mutableStateOf(false) }
    var inputTopUpAmount by rememberSaveable { mutableDoubleStateOf(0.0) }
    val balance = viewModel.balance.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BitcoinPriceText()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(balance.data != null ){
                Text(
                    "Your balance: ${balance.data?.amount} BTC",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            TextButton({
                showPopup = true
            }) {
                Text(stringResource(R.string.button_top_up_balance))
            }
            //BalanceBox(showPopup = {showPopup = true})
            Button({
                onNavigateToAddTransactionScreen()
            }) {
                Text(stringResource(R.string.button_add_transaction))
            }
            TransactionList()
        }

        if (showPopup) {
            TopUpBalancePopUp(hidePopUp = {
                showPopup = false
                inputTopUpAmount = it
                viewModel.addBalance(it)
            })
        }
    }
}

@Composable
fun BalanceBox(

    showPopup : () -> Unit
) {

}

@Composable
fun TransactionList(transactionListViewModel: TransactionListViewModel = hiltViewModel()){
    val transactionList = transactionListViewModel.transactionList.collectAsState().value
    transactionList.data?.forEach {
        TransactionListItem(it)
    }
}

@Composable
fun TransactionListItem(transaction: TransactionUiModel){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(transaction.displayDate)
        Text(transaction.categoryName)
        Spacer(modifier = Modifier.weight(1f))
        Text(transaction.amountFormatted)
    }
}

/**
 * A popup dialog that allows the user to input an amount to top up their balance.
 *
 * Features:
 * - Displays a text field for entering a positive number.
 * - Provides a button to add the amount and close the popup.
 * - The popup is focusable, allowing the keyboard to appear when the text field is focused.
 *
 * @param hidePopUp A callback function to close the popup when the user finishes the action.
 */
@Composable
fun TopUpBalancePopUp(
    hidePopUp: (Double) -> Unit
) {
    var inputAmount by rememberSaveable { mutableDoubleStateOf(0.0) }

    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(focusable = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.LightGray)
        ) {
            Text(stringResource(R.string.text_top_up_balance))
            PositiveNumberInputField(
                inputAmount,
                { inputAmount = it }
            )
            Button({
                hidePopUp(inputAmount)
            }) {
                Text("Add")
            }
        }
    }
}


/**
 * A Composable that displays the current Bitcoin price.
 *
 * This composable observes the `bitcoinPrice` state from the [BitcoinPriceViewModel],
 * which contains information about the Bitcoin price and its loading/error state. Based on
 * the state, it renders one of the following:
 *
 * - A loading indicator (`CircularProgressIndicator`) when `isLoading` is true.
 * - An error message (`Text`) when `error` is not empty, which indicates something went wrong.
 * - The Bitcoin price (`Text`) when the `data` field is populated with the latest Bitcoin price.
 *
 * @param viewModel The [BitcoinPriceViewModel] which is injected using Hilt
 */
@Composable
fun BitcoinPriceText(
    viewModel: BitcoinPriceViewModel = hiltViewModel()
) {
    val bitcoinPriceState = viewModel.bitcoinPrice.collectAsState().value
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        when {
            bitcoinPriceState.isLoading -> {
                CircularProgressIndicator()
            }

            bitcoinPriceState.error.isNotEmpty() -> {
                Text(text = bitcoinPriceState.error, color = MaterialTheme.colorScheme.error)
            }

            bitcoinPriceState.data != null -> {
                Text(
                    text = "Bitcoin Price: $${bitcoinPriceState.data?.usd}",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun TransactionListScreenPreview() {
    TransactionListScreen({})
}
