package com.obrio.test.presentation.components


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

/**
 * A Text field that allows the user to input only positive numbers, including decimals.
 *
 * Features:
 * - Displays an empty field if the value is `0.0`.
 * - Automatically converts valid numeric input to a `Double`.
 * - Rejects invalid inputs (e.g., negative numbers, non-numeric characters).
 * - Updates the state via the `onValueChange` callback whenever a valid value is entered.
 * - Treats an empty input as `0.0` and updates the state accordingly.
 *
 * @param value The current value displayed in the text field as a `Double`.
 * @param onValueChange A callback function to handle updates to the value.
 * @param modifier Modifier to customize the appearance or behavior of the text field.
 */
@Composable
fun PositiveNumberInputField(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = if (value == 0.0) "" else value.toString(), // Show empty field if value is 0
        onValueChange = { input ->
            val num = input.toDoubleOrNull()
            if (num != null && num >= 0) {
                onValueChange(num)
            } else if (input.isEmpty()) {
                onValueChange(0.0) // Treat empty input as 0
            }
        },
        label = { Text("Input number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
    )
}

@Preview
@Composable
fun PositiveNumberInputFieldPreview() {
    var num by rememberSaveable { mutableDoubleStateOf(0.0) }

    PositiveNumberInputField(
        value = num,
        onValueChange = { num = it }
    )
}