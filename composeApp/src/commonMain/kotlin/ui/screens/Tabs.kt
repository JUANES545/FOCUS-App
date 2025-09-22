package ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.FocusTab
import navigation.SettingsTab
import navigation.TasksTab
import org.jetbrains.compose.ui.tooling.preview.Preview

private val TopAppBarHeight = 66.dp
private val NavigationBarHeight = 76.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsRootUI() {
    TabNavigator(FocusTab) {
        val currentTab = LocalTabNavigator.current.current
        val showTopBar = currentTab != FocusTab

        Scaffold(
            topBar = {
                if (showTopBar) {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentTab.options.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF112B3C),
                            titleContentColor = Color.White
                        )
                    )
                }
            },
            bottomBar = { BottomBar() }
        ) { innerPadding ->
            Surface(
                Modifier
                    .fillMaxSize()
                    .then(
                        if (showTopBar) Modifier.padding(
                            top = TopAppBarHeight,
                            bottom = NavigationBarHeight
                        ) else Modifier
                    )
            ) {
                CurrentTab()
            }
        }
    }
}

@Composable
private fun BottomBar() {
    val tabNavigator = LocalTabNavigator.current
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f)
            )
    ) {
        listOf(FocusTab, TasksTab, SettingsTab).forEach { tab ->
            val selected = tabNavigator.current == tab
            NavigationBarItem(
                selected = selected,
                onClick = { tabNavigator.current = tab },
                icon = {
                    when (tab) {
                        FocusTab -> Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Enfoque",
                            tint = if (selected) Color(0xFFF66B0E) else Color(0xFFA3A3A3)
                        )
                        TasksTab -> Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = "Tareas",
                            tint = if (selected) Color(0xFFF66B0E) else Color(0xFFA3A3A3)
                        )
                        SettingsTab -> Icon(
                            Icons.Default.Settings,
                            contentDescription = "Ajustes",
                            tint = if (selected) Color(0xFFF66B0E) else Color(0xFFA3A3A3)
                        )
                        else -> Icon(
                            Icons.Default.Circle,
                            contentDescription = "Tab",
                            tint = if (selected) Color(0xFFF66B0E) else Color(0xFFA3A3A3)
                        )
                    }
                },
                label = {
                    Text(
                        text = tab.options.title,
                        color = if (selected) Color(0xFFF66B0E) else Color(0xFFA3A3A3)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTabsRootUI() {
    TabsRootUI()
}
