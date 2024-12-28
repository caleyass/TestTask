package com.obrio.test.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
 */

@Composable
fun TransactionListScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

    }
}
