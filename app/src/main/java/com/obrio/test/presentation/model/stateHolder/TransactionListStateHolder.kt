package com.obrio.test.presentation.model.stateHolder

import androidx.paging.PagingData
import com.obrio.test.domain.model.Transaction
import com.obrio.test.presentation.model.TransactionUiModel

data class TransactionListStateHolder(
    override var isLoading: Boolean = false,
    override var data: PagingData<TransactionUiModel>? = null,
    override var error: String = ""
) : StateHolder<PagingData<TransactionUiModel>>