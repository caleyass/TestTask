package com.obrio.test.utils

enum class TransactionCategory(val displayName: String) {
    GROCERIES("Groceries"),
    TAXI("Taxi"),
    ELECTRONICS("Electronics"),
    RESTAURANT("Restaurant"),
    OTHER("Other");

    companion object {
        // Helper method to find a category by its display name
        fun fromDisplayName(name: String): TransactionCategory? {
            return values().find { it.displayName == name }
        }
    }
}