package com.example.compusnow.screens.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.SIGN_UP_SCREEN

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    appState: CompuSnowAppState
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState

    // Estado para controlar la visibilidad de la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    // Determinar los colores según el tema
    val backgroundColor = if (appState.isDarkTheme) Color(0xFF2A60B0) else Color(0xFFE0F7FA)
    val textFieldBorderColor = if (appState.isDarkTheme) Color.White else Color(0xFF173868)
    val buttonBackgroundColor = if (appState.isDarkTheme) Color(0xFF1B3F7D) else Color(0xFF173868)
    val textColor = if (appState.isDarkTheme) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo con curva personalizada
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, size.height * 0.3f)
                cubicTo(
                    size.width * 0.2f, size.height * 0.45f,
                    size.width * 0.8f, size.height * 0.35f,
                    size.width, size.height * 0.5f
                )
                lineTo(size.width, 0f)
                close()
            }
            // Parte superior con el color primario basado en el tema
            drawPath(
                path = path,
                color = if (appState.isDarkTheme) Color(0xFF1B3F7D) else Color(0xFF173868), // primary_dark_blue or primary_light_blue
                style = Fill
            )
        }
        // Botón de cambio de tema (oscuro/claro)
        IconButton(
            onClick = { appState.toggleTheme() }, // Cambia el tema usando el estado
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7, // Ícono según el tema
                contentDescription = "Cambiar Tema",
                tint = Color.White
            )
        }

        // Botón de cambio de tema (arriba a la derecha, igual que en el Login)
        IconButton(
            onClick = { appState.toggleTheme() },
            modifier = Modifier
                .align(Alignment.TopEnd) // Posicionado en la esquina superior derecha
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Brightness4,
                contentDescription = "Cambiar Tema",
                tint = if (!appState.isDarkTheme) Color.Black else MaterialTheme.colors.onPrimary // Cambia a negro en modo claro
            )
        }

        // Botón de cambio de tema (arriba a la derecha, igual que en el Login)
        IconButton(
            onClick = { appState.toggleTheme() },
            modifier = Modifier
                .align(Alignment.TopEnd) // Posicionado en la esquina superior derecha
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Brightness4,
                contentDescription = "Cambiar Tema",
                tint = if (!appState.isDarkTheme) Color.Black else MaterialTheme.colors.onPrimary // Cambia a negro en modo claro
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Iniciar Sesión",
                fontSize = 36.sp,
                color = Color.White, // Siempre blanco para destacar sobre cualquier fondo
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de email (con los colores definidos)
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = {
                    Text(
                        text = "E-mail",
                        color = textFieldBorderColor // Color basado en el tema
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = backgroundColor,
                    focusedBorderColor = textFieldBorderColor,
                    unfocusedBorderColor = Color.Gray,
                    textColor = textColor,
                    cursorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña (con los mismos colores)
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = {
                    Text(
                        text = "Contraseña",
                        color = textFieldBorderColor // Color basado en el tema
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (uiState.password.isNotEmpty()) {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = description,
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = backgroundColor,
                    focusedBorderColor = textFieldBorderColor,
                    unfocusedBorderColor = Color.Gray,
                    textColor = textColor,
                    cursorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones de inicio de sesión y registro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.onSignInClick(openAndPopUp) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonBackgroundColor
                    )
                ) {
                    Text(text = "Iniciar Sesión", color = Color.White)
                }

                Button(
                    onClick = { openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonBackgroundColor
                    )
                ) {
                    Text(text = "Registrarse", color = Color.White)
                }
            }
        }
    }
}
