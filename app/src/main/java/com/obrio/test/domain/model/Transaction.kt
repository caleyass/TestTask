package com.obrio.test.domain.model

import com.obrio.test.utils.TransactionCategory

data class Transaction(
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: Long
)