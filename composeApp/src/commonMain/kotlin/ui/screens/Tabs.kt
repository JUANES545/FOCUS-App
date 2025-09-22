package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import androidx.compose.material3.ExperimentalMaterial3Api

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
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
      Surface(Modifier.fillMaxSize().padding(innerPadding)) {
        CurrentTab()
      }
    }
  }
}

@Composable
private fun BottomBar() {
  val tabNavigator = LocalTabNavigator.current
  NavigationBar {
    listOf(FocusTab, TasksTab, SettingsTab).forEach { tab ->
      val selected = tabNavigator.current == tab
      NavigationBarItem(
        selected = selected,
        onClick = { tabNavigator.current = tab },
        icon = {
          when (tab) {
            FocusTab -> Text("▶️", style = MaterialTheme.typography.titleMedium)
            TasksTab -> Text("☑️", style = MaterialTheme.typography.titleMedium)
            SettingsTab -> Text("⚙️", style = MaterialTheme.typography.titleMedium)
            else -> Text("•")
          }
        },
        label = { Text(tab.options.title) }
      )
    }
  }
}
