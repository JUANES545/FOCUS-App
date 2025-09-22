package ui.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import navigation.FocusTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    onBackClick: () -> Unit = { /* Default empty function */ }
) {
    var selectedTimeframe by remember { mutableStateOf(0) } // 0: Día, 1: Semana, 2: Mes
    val timeframes = listOf("Día", "Semana", "Mes")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Estadísticas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Menu */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF112B3C),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Segmented Control
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    timeframes.forEachIndexed { index, timeframe ->
                        val isSelected = selectedTimeframe == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .background(
                                    if (isSelected) Color(0xFF205375) else Color.Transparent,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { selectedTimeframe = index },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = timeframe,
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF4B5563),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Focus Hours Chart Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(373.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEFEF))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(17.dp)
                ) {
                    // Chart Title and Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Horas de Enfoque",
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF112B3C)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Enfoque legend
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color(0xFF205375), RoundedCornerShape(9999.dp))
                                )
                                Text(
                                    text = "Enfoque",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }

                            // Descanso legend
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color(0xFFF66B0E), RoundedCornerShape(9999.dp))
                                )
                                Text(
                                    text = "Descanso",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Chart Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(192.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                    ) {
                        // Y-axis labels
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 8.dp, top = 12.dp)
                        ) {
                            for (i in 0..5) {
                                val value = (5 - i) * 2
                                Text(
                                    text = value.toString(),
                                    fontSize = 12.sp,
                                    color = Color(0xFF666666),
                                    modifier = Modifier.height(24.dp)
                                )
                            }
                        }

                        // Stacked Bar Chart using Canvas
                        androidx.compose.foundation.Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 32.dp, end = 16.dp, top = 12.dp, bottom = 24.dp)
                        ) {
                            val chartWidth = size.width * 0.8f
                            val chartHeight = size.height * 0.7f
                            val startX = size.width * 0.1f
                            val startY = size.height * 0.15f
                            val barWidth = chartWidth / 7f

                            // Sample data for the chart
                            val focusData = listOf(2f, 3f, 1.5f, 4f, 2.5f, 2f, 1.5f)
                            val breakData = listOf(1f, 1.5f, 2f, 1f, 1.5f, 2f, 1f)

                            // Draw bars
                            for (i in 0..6) {
                                val x = startX + i * barWidth + barWidth * 0.1f
                                val barWidthActual = barWidth * 0.8f

                                // Break bar (bottom)
                                val breakHeight = (breakData[i] / 10f) * chartHeight
                                val breakY = startY + chartHeight - breakHeight
                                drawRect(
                                    color = Color(0xFFF66B0E),
                                    topLeft = Offset(x, breakY),
                                    size = androidx.compose.ui.geometry.Size(barWidthActual, breakHeight)
                                )

                                // Focus bar (top)
                                val focusHeight = (focusData[i] / 10f) * chartHeight
                                val focusY = breakY - focusHeight
                                drawRect(
                                    color = Color(0xFF205375),
                                    topLeft = Offset(x, focusY),
                                    size = androidx.compose.ui.geometry.Size(barWidthActual, focusHeight)
                                )
                            }

                            // Draw grid lines
                            for (i in 0..5) {
                                val y = startY + (i * chartHeight / 5f)
                                drawLine(
                                    color = Color(0xFFEFEFEF),
                                    start = Offset(startX, y),
                                    end = Offset(startX + chartWidth, y),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }

                            // Draw Y-axis labels (simplified for cross-platform compatibility)
                            // Note: Text drawing in Canvas is complex in Compose Multiplatform
                            // We'll use Text composables positioned over the canvas instead
                        }

                        // X-axis labels
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach { day ->
                                Text(
                                    text = day,
                                    fontSize = 12.sp,
                                    lineHeight = 15.sp,
                                    color = Color(0xFF666666),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Daily Summary
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "6.5h",
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF112B3C),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Hoy",
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                                color = Color(0xFF6B7280),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Weekly Summary Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        androidx.compose.ui.graphics.Brush.horizontalGradient(
                            colors = listOf(Color(0xFF205375), Color(0xFF112B3C))
                        ),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resumen Semanal",
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Goal Achieved
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "85%",
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF66B0E),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Meta Alcanzada",
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Active Days
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "7",
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Días Activos",
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Average
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "4.1h",
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Promedio",
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
