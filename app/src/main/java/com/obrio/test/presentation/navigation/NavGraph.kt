package com.obrio.test.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.obrio.test.presentation.screens.AddTransactionScreen
import com.obrio.test.presentation.screens.TransactionListScreen
import kotlinx.serialization.Serializable

@Serializable
object TransactionList

@Serializable
object AddTransaction

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = TransactionList){
        composable<TransactionList>{
            TransactionListScreen ( onNavigateToAddTransactionScreen = { navController.navigate(AddTransaction) })
        }
        composable<AddTransaction> {
            AddTransactionScreen (onBackNavigate = {navController.safeNavigateBackComposable()})
        }
    }
}