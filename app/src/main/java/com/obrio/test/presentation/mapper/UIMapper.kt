package com.obrio.test.presentation.mapper

import com.obrio.test.domain.model.Transaction
import com.obrio.test.presentation.model.TransactionUiModel
import com.obrio.test.utils.convertMillisToDate

fun Transaction.toTransactionUiModel(): TransactionUiModel {
    return TransactionUiModel(
        amountFormatted = "${this.amount} BTC",
        categoryName = this.category.displayName,
        displayDate = this.timestamp.convertMillisToDate()
    )
}