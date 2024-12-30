package com.obrio.test.data.mapper

import com.obrio.test.data.local.entities.BalanceEntity
import com.obrio.test.data.local.entities.TransactionEntity
import com.obrio.test.data.network.model.BitcoinPriceResponseDto
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.domain.model.Transaction

/**
 * Map
 * BitcoinPriceResponseDto (Network bitcoin price model) to
 * BitcoinPrice (Domain bitcoin price model)
 * */
fun BitcoinPriceResponseDto.toBitcoinPrice(): BitcoinPrice =
    BitcoinPrice(usd = bpi.USD.rate_float)

/**
 * Map
 * Double (Database price model) to
 * BitcoinPrice (Domain bitcoin price model)
 * */
fun Double.toBitcoinPrice(): BitcoinPrice =
    BitcoinPrice(usd = this)

fun Transaction.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        id = 0, // Auto-generated by Room
        amount = amount,
        category = category,
        timestamp = timestamp
    )

fun TransactionEntity.toTransaction(): Transaction =
    Transaction(
        amount = amount,
        category = category,
        timestamp = timestamp
    )

fun Balance.toBalanceEntity(): BalanceEntity =
    BalanceEntity(amount = amount)

fun BalanceEntity.toBalance(): Balance =
    Balance(this.amount)