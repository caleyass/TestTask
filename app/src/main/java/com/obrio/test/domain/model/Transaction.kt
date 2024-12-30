package com.obrio.test.domain.model

import com.obrio.test.utils.TransactionCategory

data class Transaction(
    val id : Int,
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: Long
)