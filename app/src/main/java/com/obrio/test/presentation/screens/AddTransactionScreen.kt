package com.obrio.test.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obrio.test.R
import com.obrio.test.presentation.components.PositiveNumberInputField
import com.obrio.test.utils.TransactionCategory

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
fun AddTransactionScreen(
    onBackNavigate: () -> Unit
) {
    var inputAmount by rememberSaveable { mutableDoubleStateOf(0.0) }
    var selectedCategory by rememberSaveable { mutableStateOf(TransactionCategory.GROCERIES) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PositiveNumberInputField(inputAmount, { inputAmount = it })
        TransactionCategoryDropDown(
            selectedCategory.displayName
        ) { selectedCategory = it }
        Spacer(modifier = Modifier.height(20.dp))
        Button({
            onBackNavigate()
        }) {
            Text(stringResource(R.string.button_add))
        }
    }
}

/**
 * A composable dropdown menu for selecting a transaction category.
 *
 * Features:
 * - Displays the currently selected category in an `OutlinedTextField`.
 * - Shows a dropdown menu with all available categories when the dropdown is expanded.
 * - Updates the selected category via the `onValueChanged` callback when a category is selected.
 * - Prevents manual input in the text field by making it read-only.
 *
 * @param value The current selected category as a string (e.g., "Groceries", "Taxi").
 * @param onValueChanged A callback function to update the selected category when a new category is selected.
 */
@Composable
fun TransactionCategoryDropDown(
    value : String,
    onValueChanged : (TransactionCategory) -> Unit
){
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value, // Display selected category
            onValueChange = {}, // No manual input allowed
            readOnly = true, // Prevent editing
            label = { Text(stringResource(R.string.text_transaction_category)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.text_expand_menu))
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TransactionCategory.entries.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(category.displayName)
                    },
                    onClick = {
                        onValueChanged(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

