package com.example.threemobilegame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threemobilegame.ui.theme.ThreeMobileGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeMobileGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ButtonToThreeGame()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonToThreeGame() {
    ButtonToGame()
    //ButtonToQuizGame()
    //ButtonTo24Game()
}


@Composable
fun ButtonToGame(modifier: Modifier = Modifier) {
    val context1 = LocalContext.current
    val context2 = LocalContext.current
    val context3 = LocalContext.current

    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),

        ) {


            Button(onClick = {
                val intent1 = Intent(context1, GuessNumber::class.java)
                context1.startActivity(intent1)
            }) {
                Text(text = "Guess Game",fontSize = 40.sp)

            }


            Button(onClick = {
                val intent2 = Intent(context2, Quiz::class.java)
                context2.startActivity(intent2)
            }) {
                Text(text = "Quiz Game",fontSize = 40.sp)
            }


            Button(onClick = {
                val intent3 = Intent(context3, TwentyFourQuiz::class.java)
                context3.startActivity(intent3)
            }) {
                Text(text = "24 Game",fontSize = 40.sp)
            }


        }
    }
}
