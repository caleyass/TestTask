package com.obrio.test.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.obrio.test.data.local.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getTransactionsPaged(): PagingSource<Int, TransactionEntity>

}