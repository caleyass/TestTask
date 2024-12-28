package com.obrio.test.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable for the Add Transaction Screen.
 * This screen allows the user to input a transaction amount, select a category,
 * and add the transaction. Upon completion, it navigates back to the Transaction List Screen.
 *
 * Features:
 * - Number input field for entering the transaction amount.
 * - Dropdown for selecting a category (groceries, taxi, electronics, restaurant, other).
 * - Button to confirm and add the transaction.
 * - Navigation back to the Transaction List Screen upon successful submission.
 */
@Composable
fun AddTransactionScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

    }
}

