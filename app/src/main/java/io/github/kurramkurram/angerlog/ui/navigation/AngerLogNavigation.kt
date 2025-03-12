package io.github.kurramkurram.angerlog.ui.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepositoryImpl
import io.github.kurramkurram.angerlog.ui.screen.analysis.Analysis
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisScreen
import io.github.kurramkurram.angerlog.ui.screen.calendar.Calendar
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarScreen
import io.github.kurramkurram.angerlog.ui.screen.home.Home
import io.github.kurramkurram.angerlog.ui.screen.home.HomeScreen
import io.github.kurramkurram.angerlog.ui.screen.initial.Initial
import io.github.kurramkurram.angerlog.ui.screen.initial.InitialScreen
import io.github.kurramkurram.angerlog.ui.screen.license.License
import io.github.kurramkurram.angerlog.ui.screen.license.LicenseScreen
import io.github.kurramkurram.angerlog.ui.screen.Permission
import io.github.kurramkurram.angerlog.ui.screen.PermissionScreen
import io.github.kurramkurram.angerlog.ui.screen.Policy
import io.github.kurramkurram.angerlog.ui.screen.PolicyScreen
import io.github.kurramkurram.angerlog.ui.screen.register.Register
import io.github.kurramkurram.angerlog.ui.screen.register.RegisterScreen
import io.github.kurramkurram.angerlog.ui.screen.setting.Setting
import io.github.kurramkurram.angerlog.ui.screen.setting.SettingScreen
import io.github.kurramkurram.angerlog.ui.screen.tips.Tips
import io.github.kurramkurram.angerlog.ui.screen.tips.TipsScreen
import io.github.kurramkurram.angerlog.util.isPermissionGranted
import io.github.kurramkurram.angerlog.util.requestPermission

@Composable
fun AngerLogNavHost(
    navController: NavHostController,
    agreementPolicyRepository: AgreementPolicyRepository = AgreementPolicyRepositoryImpl()
) {
    val context = LocalContext.current
    val startDestination: Any = if (agreementPolicyRepository.hasAgree(context)) Home else Initial

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Initial> {

            val isGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                isPermissionGranted(context, requestPermission)
            } else {
                true
            }
            val route: Any = if (isGranted) Home else Permission
            InitialScreen(
                onClickLicense = {
                    navController.navigate(route = Policy)
                },
                onClick = {
                    navController.navigate(route = route) {
                        popUpTo(route = Initial) { inclusive = true }
                    }
                })
        }

        composable<Permission> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PermissionScreen(
                    onPermissionGranted = {
                        navController.navigate(route = Home) {
                            popUpTo(route = Permission) { inclusive = true }
                        }
                    },
                    onClickSkip = {
                        navController.navigate(route = Home) {
                            popUpTo(route = Permission) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable<Home> {
            HomeScreen(
                onClick = { id -> navController.navigate(route = Register(id = id)) }
            )
        }

        composable<Analysis> { AnalysisScreen() }

        composable<Calendar> {
            CalendarScreen(
                onItemClick = { id, date ->
                    val route = Register(id = id, date = date)
                    navController.navigate(route = route)
                }
            )
        }

        composable<Setting> {
            SettingScreen(
                onItemTipsClick = { navController.navigate(route = Tips) },
                onPolicyClick = { navController.navigate(route = Policy) },
                onLicenseClick = { navController.navigate(route = License) }
            )
        }

        composable<Register> { backStackEntry ->
            val id = backStackEntry.toRoute<Register>().id
            val date = backStackEntry.toRoute<Register>().date
            RegisterScreen(
                id = id,
                inputDate = date,
                onSaveClicked = { navController.popBackStack() },
                onClickBack = { navController.popBackStack() })
        }
//
//        composable<LookBack> {
//            LookBackScreen(
//                onClickBack = { navController.popBackStack() },
//                onItemClick = { })
//        }

        composable<Tips> { TipsScreen(onClickBack = { navController.popBackStack() }) }

        composable<License> { LicenseScreen(onClickButton = { navController.popBackStack() }) }

        composable<Policy> { PolicyScreen(onClickBack = { navController.popBackStack() }) }
    }
}
