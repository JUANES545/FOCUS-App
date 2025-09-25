package ui.screens.tasks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import navigation.LocalMainNavigator
import navigation.TabsRootScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.TopBar
// Removed kotlinx.datetime imports for iOS compatibility

@Composable
fun CreateTaskScreen(
    onNavigateBack: (() -> Unit)? = null
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("mm/dd/yyyy") }
    var showDatePicker by remember { mutableStateOf(false) }
    var pomodoroCount by remember { mutableStateOf(1) }
    var selectedCategory by remember { mutableStateOf(TaskCategory.ESTUDIO) }

    val mainNavigator = LocalMainNavigator.current
    val hapticFeedback = LocalHapticFeedback.current

    // Función para navegar de vuelta a TasksScreen
    val navigateBack = {
        // Usar el navegador principal para regresar
        mainNavigator?.pop()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Nueva Tarea",
                    showBackButton = true,
                    showSearchButton = false,
                    showMenuButton = false,
                    onBackClick = {
                        // Regresar a la pantalla anterior (TasksScreen)
                        onNavigateBack?.invoke() ?: navigateBack()
                    }
                )
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = "createTask",
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { it / 3 },
                        animationSpec = tween(400, easing = EaseOutCubic)
                    ) + fadeIn(animationSpec = tween(400)) togetherWith
                    slideOutHorizontally(
                        targetOffsetX = { -it / 3 },
                        animationSpec = tween(400, easing = EaseInCubic)
                    ) + fadeOut(animationSpec = tween(400))
                },
                label = "createTaskTransition"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(Modifier.height(24.dp))

                    // Task Title Input
                    TaskTitleInput(
                        title = taskTitle,
                        onTitleChange = { taskTitle = it }
                    )

                    // Task Description Input
                    TaskDescriptionInput(
                        description = taskDescription,
                        onDescriptionChange = { taskDescription = it }
                    )

                    // Due Date Input
                    DueDateInput(
                        date = selectedDate,
                        onDateChange = { selectedDate = it },
                        onDatePickerClick = { showDatePicker = true }
                    )

                    // Pomodoro Counter
                    PomodoroCounter(
                        count = pomodoroCount,
                        onCountChange = { pomodoroCount = it }
                    )

                    // Category Selection
                    CategorySelection(
                        selectedCategory = selectedCategory,
                        onCategoryChange = { selectedCategory = it }
                    )

                    Spacer(Modifier.height(32.dp))

                    // Action Buttons
                    ActionButtons(
                        onSave = {
                            // TODO: Implement save task
                            onNavigateBack?.invoke() ?: navigateBack()
                        },
                        onCancel = {
                            onNavigateBack?.invoke() ?: navigateBack()
                        }
                    )

                    Spacer(Modifier.height(32.dp))
                }
            }

            // Date Picker Dialog
            if (showDatePicker) {
                DatePickerDialog(
                    onDateSelected = { dateString ->
                        selectedDate = dateString
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }
}

@Composable
private fun TaskTitleInput(
    title: String,
    onTitleChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = "Título de la tarea",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666),
                    lineHeight = 19.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFEFEFEF),
                unfocusedBorderColor = Color(0xFFEFEFEF),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF666666),
                unfocusedLabelColor = Color(0xFF666666)
            ),
            shape = RoundedCornerShape(4.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = androidx.compose.ui.text.input.ImeAction.Next
            )
        )
    }
}

@Composable
private fun TaskDescriptionInput(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = "Descripción",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666),
                    lineHeight = 19.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(98.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFEFEFEF),
                unfocusedBorderColor = Color(0xFFEFEFEF),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF666666),
                unfocusedLabelColor = Color(0xFF666666)
            ),
            shape = RoundedCornerShape(4.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = androidx.compose.ui.text.input.ImeAction.Next
            )
        )
    }
}

@Composable
private fun DueDateInput(
    date: String,
    onDateChange: (String) -> Unit,
    onDatePickerClick: () -> Unit
) {
    Column {
        // Custom date input field with floating label
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onDatePickerClick() }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Floating label
            Text(
                text = "Fecha de entrega",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFF66B0E),
                lineHeight = 14.sp,
                modifier = Modifier.offset(y = (-8).dp)
            )

            // Date text
            Text(
                text = if (date == "mm/dd/yyyy") "mm/dd/yyyy" else date,
                fontSize = 18.sp,
                color = if (date == "mm/dd/yyyy") Color(0xFF999999) else Color.Black,
                lineHeight = 24.sp,
                modifier = Modifier.offset(y = 8.dp)
            )

            // Calendar icon
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Seleccionar fecha",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd),
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun PomodoroCounter(
    count: Int,
    onCountChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(
                Color(0xFFEFEFEF),
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Pomodoros estimados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF205375),
                lineHeight = 19.sp
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrease Button
                Button(
                    onClick = { if (count > 1) onCountChange(count - 1) },
                    modifier = Modifier
                        .size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFF205375)
                    )
                }

                Spacer(Modifier.width(16.dp))

                // Count Display
                Text(
                    text = count.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF112B3C),
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(40.dp)
                )

                Spacer(Modifier.width(16.dp))

                // Increase Button
                Button(
                    onClick = { onCountChange(count + 1) },
                    modifier = Modifier
                        .size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFF205375)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySelection(
    selectedCategory: TaskCategory,
    onCategoryChange: (TaskCategory) -> Unit
) {
    Column {
        Text(
            text = "Categoría",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF205375),
            lineHeight = 24.sp
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Estudio Button
            CategoryButton(
                text = "Estudio",
                icon = Icons.Default.School,
                isSelected = selectedCategory == TaskCategory.ESTUDIO,
                onClick = { onCategoryChange(TaskCategory.ESTUDIO) },
                selectedColor = Color(0xFFF66B0E),
                unselectedColor = Color(0xFFEFEFEF)
            )

            // Personal Button
            CategoryButton(
                text = "Personal",
                icon = Icons.Default.Person,
                isSelected = selectedCategory == TaskCategory.PERSONAL,
                onClick = { onCategoryChange(TaskCategory.PERSONAL) },
                selectedColor = Color(0xFFF66B0E),
                unselectedColor = Color(0xFFEFEFEF)
            )

            // Trabajo Button
            CategoryButton(
                text = "Trabajo",
                icon = Icons.Default.Work,
                isSelected = selectedCategory == TaskCategory.TRABAJO,
                onClick = { onCategoryChange(TaskCategory.TRABAJO) },
                selectedColor = Color(0xFFF66B0E),
                unselectedColor = Color(0xFFEFEFEF)
            )
        }
    }
}

@Composable
private fun CategoryButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) selectedColor else unselectedColor
        ),
        shape = RoundedCornerShape(9999.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(12.dp),
                tint = if (isSelected) Color.White else Color(0xFF205375)
            )

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color(0xFF205375),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ActionButtons(
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Save Button
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF66B0E)
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Guardar tarea",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 19.sp
                )
            }

            // Cancel Button
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF205375)
                ),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF205375)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF205375),
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar fecha",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF205375)
            )
        },
        text = {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.padding(16.dp)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        // Simple date formatting for iOS compatibility
                        // Convert milliseconds to date components
                        val totalDays = millis / (24 * 60 * 60 * 1000)
                        val epochDays = totalDays + 719163 // Days since epoch (1970-01-01)

                        // Calculate year, month, day (simplified algorithm)
                        val year = 1970 + (epochDays / 365.25).toInt()
                        val remainingDays = epochDays - ((year - 1970) * 365.25).toInt()
                        val month = (remainingDays / 30.44).toInt() + 1
                        val day = (remainingDays % 30.44).toInt() + 1

                        val monthStr = month.toString().padStart(2, '0')
                        val dayStr = day.toString().padStart(2, '0')
                        onDateSelected("$monthStr/$dayStr/$year")
                    }
                }
            ) {
                Text(
                    text = "Seleccionar",
                    color = Color(0xFFF66B0E),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancelar",
                    color = Color(0xFF666666)
                )
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

enum class TaskCategory {
    ESTUDIO, PERSONAL, TRABAJO
}

@Preview
@Composable
fun CreateTaskScreenPreview() {
    Box(
        modifier = Modifier
            .size(width = 400.dp, height = 800.dp)
            .background(Color.White)
    ) {
        MaterialTheme {
            CreateTaskScreen()
        }
    }
}
