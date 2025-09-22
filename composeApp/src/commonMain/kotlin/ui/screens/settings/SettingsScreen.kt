package ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.AuthController
import navigation.LocalAuthController
import ui.components.SettingSwitchRow
import ui.theme.LocalThemeController
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val auth = LocalAuthController.current
    val themeController = LocalThemeController.current

    // Estados para sliders de Pomodoro
    var focusTime by remember { mutableStateOf(25f) }
    var shortBreak by remember { mutableStateOf(5f) }
    var longBreak by remember { mutableStateOf(15f) }

    // Estados para switches de notificaciones
    var alertsEnabled by remember { mutableStateOf(true) }
    var soundsEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(false) }

    Scaffold(
        // Eliminamos bottomBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),
        ) {
            // Configuración Pomodoro
            PomodoroConfigurationSection(
                focusTime = focusTime,
                onFocusTimeChange = { focusTime = it },
                shortBreak = shortBreak,
                onShortBreakChange = { shortBreak = it },
                longBreak = longBreak,
                onLongBreakChange = { longBreak = it }
            )

            // Notificaciones
            NotificationsSection(
                alertsEnabled = alertsEnabled,
                onAlertsChange = { alertsEnabled = it },
                soundsEnabled = soundsEnabled,
                onSoundsChange = { soundsEnabled = it },
                vibrationEnabled = vibrationEnabled,
                onVibrationChange = { vibrationEnabled = it }
            )

            // Otros Ajustes
            OtherSettingsSection(auth, themeController)

            Spacer(Modifier.height(40.dp)) // Espacio antes del botón

            Button(
                onClick = { /* TODO: Save settings */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Guardar cambios",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(24.dp)) // Espacio extra al final
        }
    }
}

@Composable
private fun PomodoroConfigurationSection(
    focusTime: Float,
    onFocusTimeChange: (Float) -> Unit,
    shortBreak: Float,
    onShortBreakChange: (Float) -> Unit,
    longBreak: Float,
    onLongBreakChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Título de sección
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Configuración Pomodoro",
                tint = Color(0xFFF66B0E), // Accent color
                modifier = Modifier
                    .size(34.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Configuración Pomodoro",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Ajusta los tiempos de trabajo y descanso",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Slider de Duración de Enfoque
        PomodoroSlider(
            label = "Tiempo de enfoque",
            value = focusTime,
            onValueChange = onFocusTimeChange,
            valueRange = 15f..60f,
            step = 5f,
            minLabel = "15 min",
            maxLabel = "60 min"
        )

        Spacer(Modifier.height(24.dp))

        // Slider de Descanso Corto
        PomodoroSlider(
            label = "Descanso corto",
            value = shortBreak,
            onValueChange = onShortBreakChange,
            valueRange = 3f..15f,
            step = 1f,
            minLabel = "3 min",
            maxLabel = "15 min"
        )

        Spacer(Modifier.height(24.dp))

        // Slider de Descanso Largo
        PomodoroSlider(
            label = "Descanso largo",
            value = longBreak,
            onValueChange = onLongBreakChange,
            valueRange = 10f..30f,
            step = 5f,
            minLabel = "10 min",
            maxLabel = "30 min"
        )
    }
}

@Composable
private fun PomodoroSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    step: Float,
    minLabel: String,
    maxLabel: String
) {
    Column(
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${value.toInt()} min",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFF66B0E), // Accent color
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = ((valueRange.endInclusive - valueRange.start) / step - 1).toInt(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF0075FF), // New blue color
                activeTrackColor = Color(0xFF0075FF), // New blue color
                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = minLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = maxLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NotificationsSection(
    alertsEnabled: Boolean,
    onAlertsChange: (Boolean) -> Unit,
    soundsEnabled: Boolean,
    onSoundsChange: (Boolean) -> Unit,
    vibrationEnabled: Boolean,
    onVibrationChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Título de sección
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificaciones",
                tint = Color(0xFFF66B0E), // Accent color
                modifier = Modifier
                    .size(34.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Configura las alertas y sonidos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Switches de notificaciones
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                SettingSwitchRow(
                    title = "Alertas",
                    subtitle = "Mostrar notificaciones",
                    state = remember { mutableStateOf(alertsEnabled) },
                    icon = Icons.Default.Info
                )

                SettingSwitchRow(
                    title = "Sonidos",
                    subtitle = "Reproducir sonidos de alerta",
                    state = remember { mutableStateOf(soundsEnabled) },
                    icon = Icons.Default.VolumeUp
                )

                SettingSwitchRow(
                    title = "Vibración",
                    subtitle = "Vibrar al finalizar",
                    state = remember { mutableStateOf(vibrationEnabled) },
                    icon = Icons.Default.PhoneAndroid
                )
            }
        }
    }
}

@Composable
private fun OtherSettingsSection(auth: AuthController, themeController: ui.theme.ThemeController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Título de sección
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Otros Ajustes",
                tint = Color(0xFFF66B0E), // Accent color
                modifier = Modifier
                    .size(34.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Otros Ajustes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                // Tema
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Tema",
                            tint = Color(0xFF205375) // Primary color
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Tema",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Modo claro/oscuro",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (themeController.isDarkTheme) "Oscuro" else "Claro",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                imageVector = if (themeController.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = "Desplegar tema",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { themeController.toggleTheme() }
                )

                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.2f)
                )

                // Cerrar sesión
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.Red
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Cerrar sesión",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Salir de tu cuenta",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { auth.onLogout() }
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    CompositionLocalProvider(
        LocalAuthController provides AuthController(
            onLoginOk = { /* no-op para preview */ },
            onLogout = { /* no-op para preview */ }
        ),
        LocalThemeController provides ui.theme.ThemeController(
            remember { mutableStateOf(false) }
        )
    ) {
        SettingsScreen()
    }
}
