package com.example.compusnow.screens.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.SIGN_UP_SCREEN

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState

    // Estado para controlar la visibilidad de la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo con curva personalizada
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 0f) // Empieza en la esquina superior izquierda
                lineTo(0f, size.height * 0.3f) // Baja por el lado izquierdo hasta la parte superior de la pantalla
                cubicTo(
                    size.width * 0.2f, size.height * 0.45f,  // Control point 1
                    size.width * 0.8f, size.height * 0.35f,  // Control point 2
                    size.width, size.height * 0.5f           // Final point en el lado derecho
                )
                lineTo(size.width, 0f) // Llega a la esquina superior derecha
                close()
            }
            // Parte superior azul curveada con primary_dark_blue
            drawPath(
                path = path,
                color = Color(0xFF0F244D), // Color primary_dark_blue
                style = Fill
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
                color = Color.White, // Cambiado a blanco
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de email sin sombras y con líneas negras suaves
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = {
                    Text(
                        text = "E-mail",
                        color = Color(0xFF2A60B0) // Azul siempre activo
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White, // Fondo blanco
                    focusedBorderColor = Color.Black, // Bordes negros suaves
                    unfocusedBorderColor = Color.Gray, // Color cuando no está enfocado
                    textColor = Color.Black, // Color del texto dentro del cuadro
                    cursorColor = Color.Black // Color del cursor al escribir
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña con visibilidad controlada por el icono de ojo
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = {
                    Text(
                        text = "Contraseña",
                        color = Color(0xFF2A60B0) // Azul siempre activo
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    // Mostrar el ícono solo cuando la contraseña no esté vacía
                    if (uiState.password.isNotEmpty()) {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = description,
                                tint = Color.Gray // Cambiar el color a negro bajo (gris suave)
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black, // Bordes negros suaves
                    unfocusedBorderColor = Color.Gray, // Color cuando no está enfocado
                    textColor = Color.Black, // Color del texto dentro del cuadro
                    cursorColor = Color.Black // Color del cursor al escribir
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
                        backgroundColor = Color(0xFF1B3F7D) // button_dark_blue
                    )
                ) {
                    Text(text = "Inicio Sesión", color = Color.White)
                }

                Button(
                    onClick = { openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF1B3F7D) // button_dark_blue
                    )
                ) {
                    Text(text = "Registrarse", color = Color.White)
                }
            }
        }
    }
}
