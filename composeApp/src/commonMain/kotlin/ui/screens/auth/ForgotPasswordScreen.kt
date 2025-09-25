package ui.screens.auth

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import navigation.LocalAuthController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.TopBar


@Composable
fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("") }
    val auth = LocalAuthController.current
    val navigator = LocalNavigator.current

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Contraseña",
                    showBackButton = true,
                    showSearchButton = false,
                    showMenuButton = false,
                    onBackClick = { navigator?.pop() }
                )
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = "forgot",
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
                label = "forgotTransition"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(24.dp))

                    // Main Content
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Padlock Icon
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF205375), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password Recovery",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        // Title
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp
                        )

                        Spacer(Modifier.height(12.dp))

                        // Description
                        Text(
                            text = "No te preocupes, ingresa tu email y te enviaremos un enlace para restablecer tu contraseña",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )

                        Spacer(Modifier.height(32.dp))

                        // Email Input
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    text = "Ingresa tu email",
                                    fontSize = 16.sp,
                                    color = Color(0xFFADAEBC)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFD1D5DB),
                                unfocusedBorderColor = Color(0xFFD1D5DB),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedLabelColor = Color(0xFFADAEBC),
                                unfocusedLabelColor = Color(0xFFADAEBC)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done
                            )
                        )

                        Spacer(Modifier.height(24.dp))

                        // Send Button
                        Button(
                            onClick = {
                                // TODO: Implement send password reset email
                                // For now, just navigate back to login
                                navigator?.pop()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E293B)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Enviar enlace",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                lineHeight = 24.sp
                            )

                            Spacer(Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        // Contact Link
                        TextButton(
                            onClick = {
                                // TODO: Implement contact support
                            }
                        ) {
                            Text(
                                text = "¿Problemas? Contáctanos",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF3B82F6),
                                lineHeight = 24.sp
                            )
                        }

                        Spacer(Modifier.height(32.dp))

                        // Information Box
                        InformationBox()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InformationBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF9FAFB),
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Color(0xFFE5E7EB),
                RoundedCornerShape(8.dp)
            )
            .padding(17.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            // Info Icon
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(
                        Color(0xFFF97316),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }

            Spacer(Modifier.width(12.dp))

            // Info Text
            Text(
                text = "Revisa tu bandeja de spam si no recibes el email en los próximos minutos.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF374151),
                lineHeight = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    Box(
        modifier = Modifier
            .size(width = 400.dp, height = 800.dp)
            .background(Color.White)
    ) {
        MaterialTheme {
            ForgotPasswordScreen()
        }
    }
}
