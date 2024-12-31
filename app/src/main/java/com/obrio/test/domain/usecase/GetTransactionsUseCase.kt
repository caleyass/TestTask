package com.obrio.test.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.obrio.test.data.local.entities.TransactionEntity
import com.obrio.test.data.mapper.toTransaction
import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.repository.FinanceRepository
import com.obrio.test.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val financeRepository: FinanceRepository
) {

    operator fun invoke() : Flow<PagingData<Transaction>> = Pager(
        PagingConfig(
            pageSize = Constants.PAGING_SIZE
        )){
            financeRepository.getTransactionsPaged()
        }
            .flow
        .map { value : PagingData<TransactionEntity> ->
            value.map { transactionEntity : TransactionEntity ->
                transactionEntity.toTransaction()
            }
        }

}