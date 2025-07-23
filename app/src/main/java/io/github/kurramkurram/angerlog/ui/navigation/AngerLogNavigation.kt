package io.github.kurramkurram.angerlog.ui.navigation

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.kurramkurram.angerlog.ACTION_PIN_WIDGET
import io.github.kurramkurram.angerlog.PinWidgetBroadcastReceiver
import io.github.kurramkurram.angerlog.RegisterWidgetReceiver
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepositoryImpl
import io.github.kurramkurram.angerlog.model.StartApp
import io.github.kurramkurram.angerlog.model.StartAppType
import io.github.kurramkurram.angerlog.ui.screen.aboutapp.AboutApp
import io.github.kurramkurram.angerlog.ui.screen.aboutapp.AboutAppScreen
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
import io.github.kurramkurram.angerlog.ui.screen.news.News
import io.github.kurramkurram.angerlog.ui.screen.news.NewsScreen
import io.github.kurramkurram.angerlog.ui.screen.newsdetail.NewsDetail
import io.github.kurramkurram.angerlog.ui.screen.newsdetail.NewsDetailScreen
import io.github.kurramkurram.angerlog.ui.screen.permission.Permission
import io.github.kurramkurram.angerlog.ui.screen.permission.PermissionScreen
import io.github.kurramkurram.angerlog.ui.screen.policy.Policy
import io.github.kurramkurram.angerlog.ui.screen.policy.PolicyScreen
import io.github.kurramkurram.angerlog.ui.screen.register.Register
import io.github.kurramkurram.angerlog.ui.screen.register.RegisterScreen
import io.github.kurramkurram.angerlog.ui.screen.setting.Setting
import io.github.kurramkurram.angerlog.ui.screen.setting.SettingScreen
import io.github.kurramkurram.angerlog.ui.screen.tips.Tips
import io.github.kurramkurram.angerlog.ui.screen.tips.TipsScreen
import io.github.kurramkurram.angerlog.util.L
import io.github.kurramkurram.angerlog.util.isPermissionGranted
import io.github.kurramkurram.angerlog.util.requestPermission

/**
 * 画面遷移の制御.
 *
 * @param startApp アプリ起動時のデータ
 * @param navController ナビゲーションを管理
 * @param agreementPolicyRepository 利用規約への同意状態を判定するRepository
 */
@Composable
fun AngerLogNavHost(
    startApp: StartApp,
    navController: NavHostController,
    agreementPolicyRepository: AgreementPolicyRepository = AgreementPolicyRepositoryImpl(),
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val startDestination: Any =
        if (agreementPolicyRepository.hasAgree(context)) {
            if (startApp.startAppType == StartAppType.REGISTER) {
                val angerLevel = startApp.angerLevel
                Register(id = 0, angerLevelType = angerLevel)
            } else {
                Home
            }
        } else {
            Initial
        }
    L.d("startDestination = $startDestination")

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Initial> {
            val isGranted =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
                },
            )
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
                    },
                )
            }
        }

        composable<Home> {
            HomeScreen(
                onItemClick = { id -> navController.navigate(route = Register(id = id)) },
                onPolicyClick = { navController.navigate(route = Policy) },
            )
        }

        composable<Analysis> { AnalysisScreen() }

        composable<Calendar> {
            CalendarScreen(
                onItemClick = { id, date ->
                    val route = Register(id = id, date = date)
                    navController.navigate(route = route)
                },
            )
        }

        composable<Setting> {
            SettingScreen(
                onAboutAppClick = { navController.navigate(route = AboutApp) },
                onItemTipsClick = { navController.navigate(route = Tips) },
                onWidgetClick = { startWidget(context) },
                onNewsClick = { navController.navigate(route = News) },
                onPolicyClick = { navController.navigate(route = Policy) },
                onLicenseClick = { navController.navigate(route = License) },
            )
        }

        composable<Register> { backStackEntry ->
            val onBackAction = {
                if (startApp.startAppType == StartAppType.REGISTER) {
                    activity?.finishAndRemoveTask()
                } else {
                    navController.popBackStack()
                }
            }

            val id = backStackEntry.toRoute<Register>().id
            val date = backStackEntry.toRoute<Register>().date
            val angerLevelType = backStackEntry.toRoute<Register>().angerLevelType
            val register = Register(id = id, date = date, angerLevelType = angerLevelType)
            RegisterScreen(
                register = register,
                onSaveClicked = { onBackAction() },
                onClickBack = { onBackAction() },
            )
        }

        composable<AboutApp> { AboutAppScreen(onClickBack = { navController.popBackStack() }) }

        composable<Tips> { TipsScreen(onClickBack = { navController.popBackStack() }) }

        composable<License> { LicenseScreen(onClickButton = { navController.popBackStack() }) }

        composable<Policy> { PolicyScreen(onClickBack = { navController.popBackStack() }) }

        composable<News> {
            NewsScreen(
                onClickBack = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate(route = NewsDetail(newsId = id)) },
            )
        }

        composable<NewsDetail> { backStackEntry ->
            val id = backStackEntry.toRoute<NewsDetail>().newsId
            NewsDetailScreen(newsId = id, onClickBack = { navController.popBackStack() })
        }
    }
}

/**
 * ウィジェットを追加する.
 *
 * @param context [Context]
 */
private fun startWidget(context: Context) {
    val manager = AppWidgetManager.getInstance(context)
    val provider = ComponentName(context, RegisterWidgetReceiver::class.java)
    val intent = Intent(context, PinWidgetBroadcastReceiver::class.java)
    intent.action = ACTION_PIN_WIDGET
    val successCallback = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    manager.requestPinAppWidget(provider, null, successCallback)
}