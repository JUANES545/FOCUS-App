package ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val auth = LocalAuthController.current

    // Estados para sliders de Pomodoro
    var focusTime by remember { mutableStateOf(25f) }
    var shortBreak by remember { mutableStateOf(5f) }
    var longBreak by remember { mutableStateOf(15f) }

    // Estados para switches de notificaciones
    var alertsEnabled by remember { mutableStateOf(true) }
    var soundsEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Button(
                onClick = { /* TODO: Save settings */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(86.dp)
                    .padding(16.dp),
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF5F5F5)), // Fondo gris claro como en la imagen
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Título principal centrado
            Text(
                text = "Ajustes",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )

            // Subtítulo
            Text(
                text = "Ajusta los tiempos de trabajo y descanso",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

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
            OtherSettingsSection(auth)

            Spacer(Modifier.height(100.dp)) // Espacio para el botón fijo
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
            .padding(bottom = 32.dp)
    ) {

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
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "${value.toInt()} min",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFF66B0E), // Accent color
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(8.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = ((valueRange.endInclusive - valueRange.start) / step - 1).toInt(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF205375), // Primary color
                activeTrackColor = Color(0xFF205375),
                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = minLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = maxLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
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
            Text(
                text = "🔔",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Text(
            text = "Configura las alertas y sonidos",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Switches de notificaciones
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                SettingSwitchRow(
                    title = "Alertas",
                    subtitle = "Mostrar notificaciones",
                    state = remember { mutableStateOf(alertsEnabled) }
                )

                SettingSwitchRow(
                    title = "Sonidos",
                    subtitle = "Reproducir sonidos de alerta",
                    state = remember { mutableStateOf(soundsEnabled) }
                )

                SettingSwitchRow(
                    title = "Vibración",
                    subtitle = "Vibrar al finalizar",
                    state = remember { mutableStateOf(vibrationEnabled) }
                )
            }
        }
    }
}

@Composable
private fun OtherSettingsSection(auth: AuthController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Título de sección
        Text(
            text = "Otros Ajustes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                // Tema
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Tema",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Modo claro/oscuro",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    },
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Claro",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "▼",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.2f)
                )

                // Cerrar sesión
                ListItem(
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
                            color = Color.Gray
                        )
                    },
                    leadingContent = {
                        Text(
                            text = "🚪",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "▶",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
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
        )
    ) {
        SettingsScreen()
    }
}
