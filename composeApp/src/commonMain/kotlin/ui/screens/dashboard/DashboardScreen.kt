package ui.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import org.jetbrains.compose.resources.painterResource
import focus_app.composeapp.generated.resources.Res
import focus_app.composeapp.generated.resources.profile
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.launch
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.core.screen.Screen
import navigation.TasksTab
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.TabsRootUI
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// Screen object for Statistics
object StatsScreen : Screen {
    @Composable
    override fun Content() = ui.screens.stats.StatsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var selectedMode by remember { mutableStateOf(0) } // 0: Enfoque, 1: Descanso corto, 2: Descanso largo
    var isTimerRunning by remember { mutableStateOf(false) }
    var timerValue by remember { mutableStateOf("25:00") }
    var showStats by remember { mutableStateOf(false) }

    val modes = listOf("Enfoque", "Descanso corto", "Descanso largo")
    val currentDate = fechaActualFormateada() // Updated to match image

    if (showStats) {
        Navigator(StatsScreen) { navigator ->
            ui.screens.stats.StatsScreen(
                onBackClick = { showStats = false }
            )
        }
    } else {
        Scaffold(
            topBar = { } // Hidden TopAppBar
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Transparent)
            ) {
                // Header fijo
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "¡Hola, José!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = currentDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Avatar con imagen profile.png
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Color.White,
                                CircleShape
                            )
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                // Contenido scrolleable debajo del header fijo
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        // Timer Card
                        TimerCard(
                            timerValue = timerValue,
                            isRunning = isTimerRunning,
                            onStart = { isTimerRunning = true },
                            onPause = { isTimerRunning = false },
                            onReset = {
                                isTimerRunning = false
                                timerValue = "25:00"
                            },
                            onSkip = { /* TODO: Skip to next */ }
                        )
                    }

                    // Mode Chips
                    item {
                        ModeChips(
                            modes = modes,
                            selectedIndex = selectedMode,
                            onModeSelected = { selectedMode = it }
                        )
                    }

                    // Quick Actions
                    item {
                        QuickActionsGrid(
                            onStatsClick = { showStats = true }
                        )
                    }

                    // Upcoming Sessions
                    item {
                        UpcomingSessionsList()
                    }

                    item {
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TimerCard(
    timerValue: String,
    isRunning: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onSkip: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer Display with circular progress
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Circular progress background
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val strokeWidth = 8.dp.toPx()
                    val radius = (size.minDimension - strokeWidth) / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    // Background circle
                    drawCircle(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        radius = radius,
                        center = center,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
                    )

                    // Progress circle (orange segments)
                    val progressAngle = 270f // 3/4 of the circle
                    drawArc(
                        color = Color(0xFFF66B0E), // Orange color
                        startAngle = -90f,
                        sweepAngle = progressAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
                    )
                }

                // Timer text
                Text(
                    text = timerValue,
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(Modifier.height(16.dp))

            // Main action button
            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isRunning,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Iniciar ciclo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(16.dp))

            // Secondary action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onPause,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    enabled = isRunning,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pausar")
                }

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reiniciar")
                }

                OutlinedButton(
                    onClick = onSkip,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Omitir")
                }
            }
        }
    }
}

@Composable
private fun ModeChips(
    modes: List<String>,
    selectedIndex: Int,
    onModeSelected: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(modes.size) { index ->
            val isSelected = selectedIndex == index
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.05f else 1f,
                animationSpec = tween(200),
                label = "chip_scale"
            )

            FilterChip(
                onClick = { onModeSelected(index) },
                label = {
                    Text(
                        text = modes[index],
                        modifier = Modifier.scale(scale)
                    )
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Text(
                            text = "✓",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                } else null
            )
        }
    }
}

@Composable
private fun QuickActionsGrid(
    onStatsClick: () -> Unit
) {
    val tabNavigator = LocalTabNavigator.current

    Column {
        Text(
            text = "Acciones rápidas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(4) { index ->
                when (index) {
                    0 -> QuickActionCard(
                        icon = "+",
                        label = "Nueva sesión",
                        iconColor = Color(0xFFF66B0E), // Orange
                        onClick = { /* TODO: New session */ }
                    )

                    1 -> QuickActionCard(
                        icon = "Ξ",
                        label = "Estadísticas",
                        iconColor = Color(0xFF205375), // Primary blue
                        onClick = onStatsClick
                    )

                    2 -> QuickActionCard(
                        icon = "☑",
                        label = "Tareas",
                        iconColor = Color(0xFF112B3C), // Dark blue
                        onClick = {
                            // Navigate to Tasks tab
                            tabNavigator.current = TasksTab
                        }
                    )

                    3 -> QuickActionCard(
                        icon = "🔊",
                        label = "White noise",
                        iconColor = Color.Gray,
                        onClick = { /* TODO: White noise */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: String,
    label: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 120.dp)
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
                // Reset after animation
                kotlinx.coroutines.GlobalScope.launch {
                    kotlinx.coroutines.delay(150)
                    isPressed = false
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon with colored background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconColor, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class UpcomingSession(
    val title: String,
    val duration: String,
    val time: String,
    val dotColor: Color
)

@Composable
private fun UpcomingSessionsList() {
    val upcomingSessions = listOf(
        UpcomingSession("Enfoque - Proyecto Web", "25 minutos", "14:30", Color(0xFFF66B0E)),
        UpcomingSession("Descanso corto", "5 minutos", "15:00", Color(0xFF205375)),
        UpcomingSession("Enfoque - Diseño UI", "25 minutos", "15:10", Color(0xFFF66B0E))
    )

    Column {
        Text(
            text = "Próximas sesiones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                upcomingSessions.forEachIndexed { index, session ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Colored dot
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(session.dotColor, RoundedCornerShape(6.dp))
                        )

                        Spacer(Modifier.width(12.dp))

                        // Content
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = session.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = session.duration,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Time
                        Text(
                            text = session.time,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (index < upcomingSessions.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }
    }
}

private val diasSemana = listOf(
    "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
)
private val meses = listOf(
    "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
)

private fun fechaActualFormateada(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val diaSemana = diasSemana[(now.dayOfWeek.ordinal + 6) % 7] // Ajuste para que lunes sea 0
    val dia = now.dayOfMonth
    val mes = meses[now.monthNumber - 1]
    return "$diaSemana, $dia de $mes"
}

@Preview
@Composable
fun PreviewTabsRootUI() {
    TabsRootUI()
}
