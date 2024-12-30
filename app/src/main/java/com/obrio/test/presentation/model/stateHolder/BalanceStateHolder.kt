package com.obrio.test.presentation.model.stateHolder

import com.obrio.test.domain.model.Balance

data class BalanceStateHolder(
    override var isLoading: Boolean = false,
    override var data: Balance? = null,
    override var error: String = ""
) : StateHolder<Balance>