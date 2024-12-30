package com.obrio.test.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obrio.test.utils.TransactionCategory

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: TransactionCategory, // Category of the transaction (enum)
    val timestamp: Long // Time of the transaction (epoch millis)
)