package com.obrio.test.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.obrio.test.presentation.viewmodels.BitcoinPriceViewModel

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
fun TransactionListScreen() {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        BitcoinPriceText()
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
fun BitcoinPriceText(viewModel: BitcoinPriceViewModel = hiltViewModel()){
    val bitcoinPriceState = viewModel.bitcoinPrice.collectAsState().value

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
