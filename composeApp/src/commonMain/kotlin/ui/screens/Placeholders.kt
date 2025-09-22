package navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable private fun Placeholder(title: String) {
  Surface(Modifier.fillMaxSize()) {
    Column(
      Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(title, style = MaterialTheme.typography.headlineMedium)
      Spacer(Modifier.height(8.dp))
      Text("Solo navegación y barras. Contenido vendrá después.")
    }
  }
}
@Composable fun DashboardScreen() = ui.screens.dashboard.DashboardScreen()
@Composable fun StatsScreen() = ui.screens.stats.StatsScreen()
@Composable fun TasksScreen() = ui.screens.tasks.TasksScreen()
@Composable fun SettingsScreen() = ui.screens.settings.SettingsScreen()
