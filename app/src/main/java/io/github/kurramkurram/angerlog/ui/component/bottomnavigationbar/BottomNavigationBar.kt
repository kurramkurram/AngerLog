package io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.screen.analysis.Analysis
import io.github.kurramkurram.angerlog.ui.screen.calendar.Calendar
import io.github.kurramkurram.angerlog.ui.screen.home.Home
import io.github.kurramkurram.angerlog.ui.screen.setting.Setting
import org.koin.androidx.compose.koinViewModel

/**
 * ボトムナビゲーションで表示する項目.
 *
 * @param name 項目名
 * @param route 画面識別子
 * @param icon アイコン
 */
data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector,
    val badge: Boolean = false
)

/**
 * ボトムナビゲーション.
 *
 * @param modifier [Modifier]
 * @param navController ナビゲーションを管理
 * @param viewModel ボトムナビゲーションのViewModel
 */
@Composable
fun AngerLogBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BottomNavigationBarViewModel = koinViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.checkBadgeStatus(context)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val topLevelRoutes =
        listOf(
            TopLevelRoute(stringResource(R.string.home), Home, Icons.Outlined.Home),
            TopLevelRoute(stringResource(R.string.calendar), Calendar, Icons.Outlined.DateRange),
            TopLevelRoute(stringResource(R.string.analysis), Analysis, Icons.Outlined.Analytics),
            TopLevelRoute(
                stringResource(R.string.setting),
                Setting,
                Icons.Outlined.Settings,
                (state as BottomNavigationUiState.Success).settingBadge
            ),
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isShow = isShowBottomBar(currentDestination, topLevelRoutes)
    if (isShow) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            topLevelRoutes.forEach { route ->
                BottomNavigationItem(
                    icon = {
                        BadgedBox(
                            badge = {
                                if (route.badge) {
                                    Badge()
                                }
                            },
                        ) {
                            Icon(route.icon, contentDescription = route.name)
                        }
                    },
                    label = {
                        Text(
                            text = route.name,
                            fontSize = 10.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    alwaysShowLabel = false,
                    selected =
                        currentDestination?.hierarchy?.any {
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
                    },
                )
            }
        }
    }
}

/**
 * ボトムナビゲーションを表示するかを判定する.
 *
 * @param currentDestination 現在表示中の画面
 * @return true: ボトムナビゲーションを非表示
 */
private fun isShowBottomBar(
    currentDestination: NavDestination?,
    topLevelRoutes: List<TopLevelRoute<out Any>>,
): Boolean {
    val currentRoute = currentDestination?.route ?: return false
    topLevelRoutes.forEach {
        if (currentRoute.contains(it.route::class.java.name)) return true
    }
    return false
}