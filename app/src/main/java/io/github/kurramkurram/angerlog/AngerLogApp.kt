package io.github.kurramkurram.angerlog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.kurramkurram.angerlog.ui.navigation.AngerLogNavHost
import io.github.kurramkurram.angerlog.ui.component.AngerLogFloatingActionButton
import io.github.kurramkurram.angerlog.ui.component.ad.AngerLogBannerAd
import io.github.kurramkurram.angerlog.ui.screen.analysis.Analysis
import io.github.kurramkurram.angerlog.ui.screen.calendar.Calendar
import io.github.kurramkurram.angerlog.ui.screen.home.Home
import io.github.kurramkurram.angerlog.ui.screen.register.Register
import io.github.kurramkurram.angerlog.ui.screen.setting.Setting

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

/**
 * アプリ全体.
 */
@Composable
fun AngerLogApp() {
    val topLevelRoutes = listOf(
        TopLevelRoute(stringResource(R.string.home), Home, Icons.Rounded.Home),
        TopLevelRoute(stringResource(R.string.calendar), Calendar, Icons.Sharp.DateRange),
        TopLevelRoute(stringResource(R.string.analysis), Analysis, Icons.Sharp.Build),
        TopLevelRoute(stringResource(R.string.setting), Setting, Icons.Filled.Settings)
    )

    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(color = MaterialTheme.colorScheme.background),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                topLevelRoutes = topLevelRoutes
            )
        },
        floatingActionButton = { FloatingActionButton(navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                Column(modifier = Modifier.weight(1f)) {
                    AngerLogNavHost(navController)
                }

                AngerLogBannerAd()
            }
        }
    }
}

/**
 * ボトムナビゲーション.
 */
@Composable
private fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    topLevelRoutes: List<TopLevelRoute<out Any>>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isShow = isShowBottomBar(currentDestination, topLevelRoutes)
    if (isShow) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            topLevelRoutes.forEach { route ->
                BottomNavigationItem(
                    icon = { Icon(route.icon, contentDescription = route.name) },
                    label = {
                        Text(
                            modifier = modifier.padding(vertical = 5.dp),
                            text = route.name,
                            fontSize = 10.sp
                        )
                    },
                    selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(route.route::class)
                    } == true,
                    onClick = {
                        navController.navigate(route.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

/**
 * FloatingActionButton.
 */
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    navController: NavController
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

        FloatingType.LOOK_BACK -> {
//            AngerLogFloatingActionButton(modifier = modifier) {
//                navController.navigate(route = LookBack)
//            }
        }

        else -> {}
    }
}

/**
 * ボトムナビゲーションを表示するかどうかを判定する.
 *
 * @param currentDestination 現在表示中の画面
 * @return true ボトムナビゲーションを非表示
 */
private fun isShowBottomBar(
    currentDestination: NavDestination?,
    topLevelRoutes: List<TopLevelRoute<out Any>>
): Boolean {
    val currentRoute = currentDestination?.route ?: return false
    topLevelRoutes.forEach {
        if (currentRoute.contains(it.route::class.java.name)) return true
    }
    return false
}

/**
 * FloatingActionButtonを表示するかどうかを判定する.
 *
 * @param currentDestination 現在表示中の画面
 * @return FloatingType FloatingActionButtonの表示タイプを返す
 */
private fun isShowFloatingActionButton(currentDestination: NavDestination?): FloatingType {
    val currentRoute = currentDestination?.route ?: return FloatingType.NONE
    val register = listOf(
        Home::class.java.name
    )
    register.forEach {
        if (currentRoute.contains(it)) return FloatingType.REGISTER
    }

    val lookBack = listOf(
        Analysis::class.java.name
    )
    lookBack.forEach {
        if (currentRoute.contains(it)) return FloatingType.LOOK_BACK
    }

    return FloatingType.NONE
}

enum class FloatingType {
    REGISTER, // 登録画面
    LOOK_BACK, // 振り返り画面
    NONE // 表示なし
}