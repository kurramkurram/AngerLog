package io.github.kurramkurram.angerlog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.kurramkurram.angerlog.model.StartApp
import io.github.kurramkurram.angerlog.ui.component.AngerLogFloatingActionButton
import io.github.kurramkurram.angerlog.ui.component.ad.AngerLogBannerAd
import io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar.AngerLogBottomNavigationBar
import io.github.kurramkurram.angerlog.ui.navigation.AngerLogNavHost
import io.github.kurramkurram.angerlog.ui.screen.analysis.Analysis
import io.github.kurramkurram.angerlog.ui.screen.home.Home
import io.github.kurramkurram.angerlog.ui.screen.initial.Initial
import io.github.kurramkurram.angerlog.ui.screen.permission.Permission
import io.github.kurramkurram.angerlog.ui.screen.register.Register

/**
 * アプリ全体.
 *
 * @param startApp アプリ起動時のデータ
 */
@Composable
fun AngerLogApp(startApp: StartApp) {
    val navController = rememberNavController()
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .background(color = MaterialTheme.colorScheme.background),
        bottomBar = {
            AngerLogBottomNavigationBar(
                navController = navController,
            )
        },
        floatingActionButton = { FloatingActionButton(navController = navController) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                Column(modifier = Modifier.weight(1f)) {
                    AngerLogNavHost(startApp = startApp, navController = navController)
                }

                AdBanner(navController = navController)
            }
        }
    }
}

/**
 * FloatingActionButton.
 *
 * @param modifier [Modifier]
 * @param navController ナビゲーションを管理
 */
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val floatingType = isShowFloatingActionButton(currentDestination)
    when (floatingType) {
        FloatingType.REGISTER -> {
            AngerLogFloatingActionButton(modifier = modifier) {
                navController.navigate(route = Register(id = 0))
            }
        }

        else -> {}
    }
}

/**
 * バナー広告.
 *
 * @param modifier [Modifier]
 * @param navController ナビゲーションを管理
 */
@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isShowAd = isShowAdBanner(currentDestination)
    if (isShowAd) {
        AngerLogBannerAd(modifier = modifier)
    }
}

/**
 * FloatingActionButtonを表示するかを判定する.
 *
 * @param currentDestination 現在表示中の画面
 * @return FloatingType FloatingActionButtonの表示タイプを返す
 */
private fun isShowFloatingActionButton(currentDestination: NavDestination?): FloatingType {
    val currentRoute = currentDestination?.route ?: return FloatingType.NONE
    val register =
        listOf(
            Home::class.java.name,
        )
    register.forEach {
        if (currentRoute.contains(it)) return FloatingType.REGISTER
    }

    val lookBack =
        listOf(
            Analysis::class.java.name,
        )
    lookBack.forEach {
        if (currentRoute.contains(it)) return FloatingType.LOOK_BACK
    }

    return FloatingType.NONE
}

/**
 * バナー広告を表示するかを判定する.
 *
 * @param currentDestination 現在表示中の画面
 * @return true: 表示する
 */
private fun isShowAdBanner(currentDestination: NavDestination?): Boolean {
    val currentRoute = currentDestination?.route ?: return false
    val notShowDestination = listOf(Initial::class.java.name, Permission::class.java.name)
    notShowDestination.forEach {
        if (currentRoute.contains(it)) return false
    }
    return true
}

/**
 * FloatingActionButtonを表示するタイプ.
 */
enum class FloatingType {
    REGISTER, // 登録画面
    LOOK_BACK, // 振り返り画面
    NONE, // 表示なし
}
