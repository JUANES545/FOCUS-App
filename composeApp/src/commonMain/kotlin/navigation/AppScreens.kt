package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.screens.TabsRootUI

// --------- Tabs ----------
object FocusTab : Tab {
  override val options: TabOptions
    @Composable get() = TabOptions(
      index = 0u,
      title = "Enfoque",
      icon = null
    )
  @Composable override fun Content() { DashboardScreen() }
}

object TasksTab : Tab {
  override val options: TabOptions
    @Composable get() = TabOptions(
      index = 1u,
      title = "Tareas",
      icon = null
    )
  @Composable override fun Content() { TasksScreen() }
}

object SettingsTab : Tab {
  override val options: TabOptions
    @Composable get() = TabOptions(
      index = 2u,
      title = "Ajustes",
      icon = null
    )
  @Composable override fun Content() { SettingsScreen() }
}

// --------- Screens ----------
object LoginScreen : Screen {
  @Composable override fun Content() = ui.screens.auth.LoginScreen()
}

object ForgotPasswordScreen : Screen {
  @Composable override fun Content() = ui.screens.auth.ForgotPasswordScreen()
}

object SignUpScreen : Screen {
  @Composable override fun Content() = ui.screens.auth.SignUpScreen()
}

object CreateTaskScreen : Screen {
  @Composable override fun Content() = ui.screens.tasks.CreateTaskScreen()
}

object TabsRootScreen : Screen {
  @Composable override fun Content() = TabsRootUI()
}
