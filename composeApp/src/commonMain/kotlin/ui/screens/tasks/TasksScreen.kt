package ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.AuthController
import navigation.LocalAuthController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.settings.SettingsScreen
import ui.theme.Accent
import ui.theme.PrimaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    var selectedTab by remember { mutableStateOf(0) } // 0: Pendientes, 1: En curso, 2: Hechas
    val tabs = listOf("Pendientes", "En curso", "Hechas")

    // Mock data for tasks matching the image
    val allTasks = listOf(
        TaskData("Revisar propuesta de diseño UX", "Hoy, 14:30", 3, "Diseño", false),
        TaskData("Llamar al cliente sobre feedback", "Mañana, 10:00", 1, "Ventas", false),
        TaskData("Preparar presentación mensual", "Viernes, 16:00", 5, "Gestión", false),
        TaskData("Actualizar documentación técnica", "Lunes, 09:00", 2, "Desarrollo", false),
        TaskData("Revisar emails importantes", "Hoy, 18:00", 1, "Comunicación", false),
        TaskData("Revisar propuesta de diseño UX", "Hoy, 14:30", 3, "Diseño", false),
        TaskData("Preparar presentación mensual", "Mañana, 10:00", 1, "Gestión", false)
    )

    // Filter tasks based on selected tab
    val filteredTasks = when (selectedTab) {
        0 -> allTasks.filter { !it.completed }
        1 -> allTasks.filter { !it.completed } // For now, same as pending
        2 -> allTasks.filter { it.completed }
        else -> allTasks
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
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
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
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = index }
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Accent else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (isSelected) {
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(3.dp)
                                    .background(Accent, RoundedCornerShape(1.5.dp))
                            )
                        }
                    }
                }
            }

            // Task List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!completed) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                if (category != null) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Time info and hours
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⏰",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "$hours h",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = timeInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Status indicator
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Accent)
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
fun taskScreenPreview() {
    CompositionLocalProvider(
        LocalAuthController provides AuthController(
            onLoginOk = { /* no-op para preview */ },
            onLogout = { /* no-op para preview */ }
        )
    ) {
        TasksScreen()
    }
}
