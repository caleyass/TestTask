package com.obrio.test.domain.usecase

import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.repository.FinanceRepository
import javax.inject.Inject

class AddBalanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository
) {
    suspend operator fun invoke(balance: Balance){
        financeRepository.addBalance(balance)
    }
}