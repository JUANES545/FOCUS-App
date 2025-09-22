package ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
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
import ui.components.MusicPlayer

private val TopAppBarHeight = 66.dp
private val NavigationBarHeight = 76.dp

// CompositionLocal para controlar el MusicPlayer
class MusicPlayerController {
    var showMusicPlayer by mutableStateOf(false)
        private set

    fun showPlayer() {
        showMusicPlayer = true
    }

    fun hidePlayer() {
        showMusicPlayer = false
    }
}

val LocalMusicPlayerController = compositionLocalOf<MusicPlayerController> {
    error("MusicPlayerController not provided")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsRootUI() {
    val musicPlayerController = remember { MusicPlayerController() }

    CompositionLocalProvider(
        LocalMusicPlayerController provides musicPlayerController
    ) {
        TabNavigator(FocusTab) {
            val currentTab = LocalTabNavigator.current.current
            val tabNavigator = LocalTabNavigator.current
            val showTopBar = currentTab != FocusTab

            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    topBar = {
                        if (showTopBar) {
                            TopAppBar(
                                title = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = currentTab.options.title,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                },
                                navigationIcon = {
                                    if (currentTab == TasksTab || currentTab == SettingsTab) {
                                        IconButton(
                                            onClick = { tabNavigator.current = FocusTab }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Regresar",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                },
                                actions = {
                                    if (currentTab == TasksTab) {
                                        IconButton(
                                            onClick = { /* TODO: Search functionality */ }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "Buscar",
                                                tint = Color.White
                                            )
                                        }
                                        IconButton(
                                            onClick = { /* TODO: More options */ }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MoreVert,
                                                contentDescription = "Más opciones",
                                                tint = Color.White
                                            )
                                        }
                                    }
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

                // Music Player - Fuera del Scaffold para que aparezca sobre todo
                MusicPlayer(
                    isVisible = musicPlayerController.showMusicPlayer,
                    onDismiss = { musicPlayerController.hidePlayer() }
                )
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
