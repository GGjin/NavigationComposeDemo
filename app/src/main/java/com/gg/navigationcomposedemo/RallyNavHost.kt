package com.gg.navigationcomposedemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import com.gg.navigationcomposedemo.data.UserData
import com.gg.navigationcomposedemo.ui.accounts.AccountsBody
import com.gg.navigationcomposedemo.ui.accounts.SingleAccountBody
import com.gg.navigationcomposedemo.ui.bills.BillsBody
import com.gg.navigationcomposedemo.ui.overview.OverviewBody

/**
 * @description:
 * @author: Jinyu.Guo3
 * @createDate: 2021/8/19 019 14:06
 * @updateUser:
 * @updateDate: 2021/8/19 019 14:06
 */
@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RallyScreen.Overview.name,
        modifier = modifier
    ) {
        composable(RallyScreen.Overview.name) {
            OverviewBody(
                onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) },
                onAccountClick = { name ->
                    navController.navigate("${RallyScreen.Accounts.name}/$name")
                },
            )
        }
        composable(RallyScreen.Accounts.name) {
            AccountsBody(accounts = UserData.accounts) { name ->
                navController.navigate("Accounts/${name}")
            }
        }
        composable(RallyScreen.Bills.name) {
            BillsBody(bills = UserData.bills)
        }
        val accountsName = RallyScreen.Accounts.name
        composable(
            "$accountsName/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "example://rally/$accountsName/{name}"
            }),
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            SingleAccountBody(account = account)
        }
    }
}
private fun navigateToSingleAccount(
    navController: NavHostController,
    accountName: String
) {
    navController.navigate("${RallyScreen.Accounts.name}/$accountName")
}

