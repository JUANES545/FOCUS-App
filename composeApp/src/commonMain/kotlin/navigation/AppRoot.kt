package navigation

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.tasks.TasksScreen
import ui.theme.FocusTheme
import ui.theme.LocalThemeController
import ui.theme.rememberThemeController

// CompositionLocal específico para el navegador principal
val LocalMainNavigator = compositionLocalOf<Navigator> {
    error("MainNavigator not provided")
}

@Composable
fun AppRoot() {
    val themeController = rememberThemeController()

    FocusTheme(isDarkTheme = themeController.isDarkTheme) {
        // Estado simplificado de autenticación
        var isLoggedIn by remember { mutableStateOf(false) }
        // Si no hay sesión, muestra Login; al iniciar sesión, reemplaza por Tabs
        Navigator(if (isLoggedIn) TabsRootScreen else LoginScreen) { nav ->
            // Permite que el Login cambie el estado
            CompositionLocalProvider(
                LocalMainNavigator provides nav,
                LocalAuthController provides AuthController(
                onLoginOk = {
                    isLoggedIn = true
                    nav.replaceAll(TabsRootScreen)
                },
                onLogout = {
                    isLoggedIn = false
                    nav.replaceAll(LoginScreen)
                },
                onForgotPassword = {
                    nav.push(ForgotPasswordScreen)
                },
                onSignUp = {
                    nav.push(SignUpScreen)
                }
            ),
            LocalThemeController provides themeController
            ) {
                nav.lastItem.Content()
            }
        }
    }
}

// Simple "controller" para comunicar Login -> AppRoot y Logout -> Login
open class AuthController(
    val onLoginOk: () -> Unit,
    val onLogout: () -> Unit,
    val onForgotPassword: () -> Unit = {},
    val onSignUp: () -> Unit = {}
)

val LocalAuthController = compositionLocalOf<AuthController> {
    error("AuthController not provided")
}

@Preview
@Composable
fun SettingsScreenPreview() {
    CompositionLocalProvider(
        LocalAuthController provides AuthController(
            onLoginOk = { /* no-op para preview */ },
            onLogout = { /* no-op para preview */ },
            onForgotPassword = { /* no-op para preview */ },
            onSignUp = { /* no-op para preview */ }
        )
    ) {
        AppRoot()
    }
}
