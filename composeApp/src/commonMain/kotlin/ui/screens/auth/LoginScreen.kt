package ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navigation.LocalAuthController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.TextSecondary
import org.jetbrains.compose.resources.painterResource
import focus_app.composeapp.generated.resources.Res
import focus_app.composeapp.generated.resources.google_icon
import focus_app.composeapp.generated.resources.apple_icon
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val auth = LocalAuthController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(84.dp))

            // Header Section
            HeaderSection()

            Spacer(Modifier.height(44.dp))

            // Login Section
            LoginSection(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                onLoginClick = { auth.onLoginOk() }
            )

            Spacer(Modifier.height(16.dp))

            // Social Login Section
            SocialLoginSection(auth = auth)

            Spacer(Modifier.height(32.dp))

            // Footer
            FooterSection()
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo with gradient background
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF112B3C),
                            Color(0xFF205375)
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(80f, 80f)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "F",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 75.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        // App title
        Text(
            text = "F.O.C.U.S.",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF112B3C),
            lineHeight = 36.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Flexible Organizer for Concentration Using Schedules",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF4B5563),
            lineHeight = 23.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoginSection(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(336.dp)
    ) {
        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Correo electrónico",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                    lineHeight = 19.sp
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF111827),
                unfocusedBorderColor = Color(0xFF111827),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF999999),
                unfocusedLabelColor = Color(0xFF999999)
            ),
            shape = RoundedCornerShape(0.dp),
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF999999)
                )
            }
        )

        Spacer(Modifier.height(12.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Contraseña",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                    lineHeight = 19.sp
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF111827),
                unfocusedBorderColor = Color(0xFF111827),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color(0xFF999999),
                unfocusedLabelColor = Color(0xFF999999)
            ),
            shape = RoundedCornerShape(0.dp),
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(
                    onClick = onPasswordVisibilityToggle
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF999999)
                    )
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        // Forgot password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { /* TODO: Navigate to forgot password */ }
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF205375),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Login button
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = "Iniciar sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 19.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        // Create account button
        OutlinedButton(
            onClick = { /* TODO: Navigate to sign up */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color.Black),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun SocialLoginSection(auth: navigation.AuthController) {
    Column(
        modifier = Modifier.width(336.dp)
    ) {
        // Separator
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0xFF6B7280),
                thickness = 1.dp
            )

            Text(
                text = "o continúa con",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0xFF6B7280),
                thickness = 1.dp
            )
        }

        Spacer(Modifier.height(16.dp))

        // Google button
        OutlinedButton(
            onClick = { auth.onLoginOk() },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEFEF)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF374151)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Google icon from SVG
                Image(
                    painter = painterResource(Res.drawable.google_icon),
                    contentDescription = "Google",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "Continuar con Google",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151),
                    lineHeight = 24.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Apple button
        OutlinedButton(
            onClick = { auth.onLoginOk() },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEFEF)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF374151)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Apple icon from SVG
                Image(
                    painter = painterResource(Res.drawable.apple_icon),
                    contentDescription = "Apple",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "Continuar con Apple",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151),
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
private fun FooterSection() {
    Text(
        text = "© 2025 F.O.C.U.S. Todos los derechos reservados",
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun LoginScreenPreview() {
    // Simula un AuthController para el preview
    CompositionLocalProvider(
        LocalAuthController provides navigation.AuthController(
            onLoginOk = {},
            onLogout = {}
        )
    ) {
        Box(modifier = Modifier.size(width = 400.dp, height = 800.dp)) {
            MaterialTheme {
                LoginScreen()
            }
        }
    }
}
