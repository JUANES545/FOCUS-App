package ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import navigation.AuthController
import navigation.LocalAuthController
import navigation.FocusTab
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.settings.SettingsScreen
import ui.theme.Accent
import ui.theme.PrimaryDark
import ui.theme.Primary
import ui.theme.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    var selectedTab by remember { mutableStateOf(0) } // 0: Pendientes, 1: En curso, 2: Hechas
    val tabs = listOf("Pendientes", "En curso", "Hechas")
    val tabNavigator = LocalTabNavigator.current

    // Mock data for tasks matching the image
    val pendingTasks = listOf(
        TaskData("Revisar propuesta de diseño UX", "Hoy, 14:30", 3, "Diseño", false),
        TaskData("Llamar al cliente sobre feedback", "Mañana, 10:00", 1, "Ventas", false),
        TaskData("Preparar presentación mensual", "Viernes, 16:00", 5, "Gestión", false),
        TaskData("Actualizar documentación técnica", "Lunes, 09:00", 2, "Desarrollo", false),
        TaskData("Revisar emails importantes", "Hoy, 18:00", 1, "Comunicación", false)
    )

    val inProgressTasks = listOf(
        TaskData("Desarrollar nueva funcionalidad", "Hoy, 15:30", 4, "Desarrollo", false),
        TaskData("Revisar código de compañero", "Mañana, 11:00", 2, "Desarrollo", false),
        TaskData("Preparar demo para cliente", "Jueves, 14:00", 3, "Ventas", false)
    )

    val completedTasks = listOf(
        TaskData("Configurar base de datos", "Ayer, 16:00", 2, "Desarrollo", true),
        TaskData("Enviar reporte semanal", "Ayer, 17:30", 1, "Gestión", true),
        TaskData("Reunión con equipo", "Lunes, 10:00", 1, "Comunicación", true)
    )

    // Filter tasks based on selected tab
    val filteredTasks = when (selectedTab) {
        0 -> pendingTasks
        1 -> inProgressTasks
        2 -> completedTasks
        else -> pendingTasks
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add new task */ },
                containerColor = Accent,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tab Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = index }
                            .padding(vertical = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Accent else Color(0xFF205375),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 16.sp
                        )

                        if (isSelected) {
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(Accent)
                            )
                        }
                    }
                }
            }

            // Divider
            HorizontalDivider(
                color = Color(0xFFEFEFEF),
                thickness = 1.dp
            )

            // Task List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (filteredTasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (selectedTab) {
                                    0 -> "No hay tareas pendientes"
                                    1 -> "No hay tareas en curso"
                                    2 -> "No hay tareas completadas"
                                    else -> "No hay tareas"
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(filteredTasks) { task ->
                        TaskCard(
                            title = task.title,
                            timeInfo = task.subtitle,
                            hours = task.minutes,
                            category = task.priority,
                            completed = task.completed,
                            taskStatus = selectedTab, // 0: Pendientes, 1: En curso, 2: Hechas
                            onCheckedChange = { /* TODO: Update task completion */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskCard(
    title: String,
    timeInfo: String,
    hours: Int?,
    category: String?,
    completed: Boolean,
    taskStatus: Int, // 0: Pendientes, 1: En curso, 2: Hechas
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!completed) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEFEF))
    ) {
        Column(
            modifier = Modifier.padding(17.dp)
        ) {
            // Title and Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = if (completed) Color(0xFF112B3C).copy(alpha = 0.5f) else Color(
                        0xFF112B3C
                    ),
                    textDecoration = if (completed) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1f)
                )

                if (category != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFFEFEFEF),
                                RoundedCornerShape(9999.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            color = Color(0xFF112B3C)
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Time info and hours
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Clock icon
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Tiempo",
                        tint = Accent,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "$hours",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF205375)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = timeInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF205375)
                    )
                }

                // Status indicator
                val statusColor = when (taskStatus) {
                    0 -> Accent      // Pendientes - Naranja
                    1 -> Primary     // En curso - Azul
                    2 -> Success     // Hechas - Verde
                    else -> Accent
                }

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
            }
        }
    }
}

// Data class for tasks
data class TaskData(
    val title: String,
    val subtitle: String,
    val minutes: Int?,
    val priority: String?,
    val completed: Boolean
)

@Preview
@Composable
fun TaskScreenPreview() {
    // Puedes cambiar el tamaño para simular diferentes dispositivos
    Box(modifier = Modifier.size(width = 400.dp, height = 800.dp)) {
        // Si tienes un tema propio, reemplaza 'MaterialTheme' por el tuyo
        MaterialTheme {
            CompositionLocalProvider(
                LocalAuthController provides AuthController(
                    onLoginOk = { /* no-op para preview */ },
                    onLogout = { /* no-op para preview */ }
                )
            ) {
                TasksScreen()
            }
        }
    }
}
