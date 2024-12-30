package com.obrio.test.presentation.model.stateHolder

import com.obrio.test.domain.model.Transaction
import com.obrio.test.presentation.model.TransactionUiModel

data class TransactionListStateHolder(
    override var isLoading: Boolean = false,
    override var data: List<TransactionUiModel>? = null,
    override var error: String = ""
) : StateHolder<List<TransactionUiModel>>