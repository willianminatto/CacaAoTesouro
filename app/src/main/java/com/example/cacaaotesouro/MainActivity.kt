package com.example.cacaaotesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cacaaotesouro.ui.theme.CacaAoTesouroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigationController = rememberNavController()
            NavHost(
                navController = navigationController,
                startDestination = "/home"
            ) {
                composable("/home") {
                    Tela(
                        "Tela Inicial",
                        clickB1 = { navigationController.navigate("pista1") }
                    )
                }
                composable("pista1") {
                    TelaPista(
                        "Pista 1: Qual é o animal que anda com a cabeça para baixo?",
                        correctAnswer = "canguru",
                        nextScreen = { navigationController.navigate("pista2") },
                        backScreen = { navigationController.navigate("/home") }
                    )
                }
                composable("pista2") {
                    TelaPista(
                        "Pista 2: O que é que sempre está à frente, mas nunca se move?",
                        correctAnswer = "futuro",
                        nextScreen = { navigationController.navigate("pista3") },
                        backScreen = { navigationController.navigate("pista1") }
                    )
                }
                composable("pista3") {
                    TelaPista(
                        "Pista 3: Eu sou um rei, mas não tenho um reino. Quem sou eu?",
                        correctAnswer = "rei",
                        nextScreen = { navigationController.navigate("tesouro") },
                        backScreen = { navigationController.navigate("pista2") }
                    )
                }
                composable("tesouro") {
                    TelaTesouro(
                        message = "Parabéns! Você encontrou o tesouro!",
                        restartGame = {
                            navigationController.navigate("/home") {
                                popUpTo("/home") { inclusive = true }
                            }
                        }
                    )
                }

            }
            }
        }
    }


@Composable
fun Tela(
    nomeTela: String,
    clickB1: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(nomeTela)
        Button(onClick = clickB1) {
            Text("Iniciar Caça ao Tesouro")
        }
    }
}

@Composable
fun TelaPista(
    pista: String,
    correctAnswer: String,
    nextScreen: () -> Unit,
    backScreen: () -> Unit
) {

    val answer = remember { mutableStateOf("") }
    val isAnswerCorrect = remember { mutableStateOf(false) }
    val isAnswerSubmitted = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(pista)

        TextField(
            value = answer.value,
            onValueChange = { answer.value = it },
            label = { Text("Digite sua resposta") },
            modifier = Modifier.padding(16.dp)
        )

        if (isAnswerSubmitted.value) {
            if (isAnswerCorrect.value) {
                Text("Resposta Correta!", color = androidx.compose.ui.graphics.Color.Green)
            } else {
                Text("Resposta Errada. Tente novamente.", color = androidx.compose.ui.graphics.Color.Red)
            }
        }

        Button(
            onClick = {
                isAnswerSubmitted.value = true
                isAnswerCorrect.value = answer.value.lowercase() == correctAnswer.lowercase()
            }
        ) {
            Text("Verificar Resposta")
        }

        if (isAnswerCorrect.value) {
            Button(onClick = nextScreen) {
                Text("Próxima Pista")
            }
        }

        Button(onClick = backScreen) {
            Text("Voltar")
        }
    }
}

@Composable
fun TelaTesouro(
    message: String,
    restartGame: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)
        Button(onClick = restartGame) {
            Text("Recomeçar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTela() {
    Tela("Tela teste", clickB1 = {})
}
