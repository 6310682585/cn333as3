package com.example.threemobilegame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threemobilegame.ui.theme.ThreeMobileGameTheme
import com.example.threemobilegame.ui.theme.Purple200

import kotlin.random.Random

class TwentyFourQuiz : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeMobileGameTheme {
                TheTwentyFourGame()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TheTwentyFourGame() {
    RandomNum(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}


private fun calculateNum(
    sign: Int,
    firstVal: Int,
    secondVal: Int,
): Int {
    when (sign) {
        4 -> {
            return firstVal + secondVal
        }
        5 -> {
            return firstVal - secondVal
        }
        6 -> {
            return firstVal * secondVal
        }
        else -> {
            return firstVal / secondVal
        }
    }
}

fun calculateList(
    num1: Int,
    num2: Int,
    num3: Int,
    num4: Int,
    indexFirstVal: Int,
    indexSign: Int,
    indexNum: Int,
): List<Int> {
    var disappearButton = -1
    var listNumber = mutableListOf<Int>(num1, num2, num3, num4, indexFirstVal, indexSign, disappearButton)
    if (indexNum <= 3) {
        if (indexSign != -1 && indexFirstVal != indexNum) {
            listNumber[indexNum] = calculateNum(indexSign, listNumber[indexFirstVal], listNumber[indexNum])
            listNumber[indexFirstVal] = 0
            listNumber[6] = indexFirstVal
            listNumber[4] = -1
            listNumber[5] = -1
        } else if (indexSign == -1) {
            listNumber[4] = indexNum
        }
    } else {
        if (indexFirstVal != -1) {
            listNumber[5] = indexNum
        }
    }
    return listNumber
}

fun check24(
    appearButton: List<Boolean>,
    listNumber: List<Int>,
): String {
    var checkTrue = 0
    var trueBox = -1
    for (i in 0..3) {
        if (appearButton[i]) {
            checkTrue += 1
            trueBox = i
        }
    }
    if (checkTrue > 1) {
        return ""
    }
    if (listNumber[trueBox] == 24) {
        return "win"
    }
    return "lose"
}

fun buttonColor(
    indexFirstVal: Int,
    indexSign: Int,
): MutableList<Color> {
    var colorList = List(8) { Purple200 }.toMutableList()
    if (indexFirstVal != -1) {
        colorList[indexFirstVal] = Color.Cyan
    }
    if (indexSign != -1) {
        colorList[indexSign] = Color.Cyan
    }
    return  colorList
}

@Composable
fun RandomNum(modifier: Modifier = Modifier) {
    var signList = mutableListOf<Int>(R.string.plus, R.string.minus, R.string.multiply, R.string.divide)
    var randomNumber = List(4) { Random.nextInt(1, 9) }
    var listOriginalNumber by remember { mutableStateOf(randomNumber) }
    var num1 by remember { mutableStateOf(randomNumber[0]) }
    var num2 by remember { mutableStateOf(randomNumber[1]) }
    var num3 by remember { mutableStateOf(randomNumber[2]) }
    var num4 by remember { mutableStateOf(randomNumber[3]) }

    var indexFirstVal by remember { mutableStateOf(-1) }
    var indexSign by remember { mutableStateOf(-1) }
    var listNumber by remember { mutableStateOf(mutableListOf<Int>(num1, num2, num3, num4, indexFirstVal, indexSign)) }

    var appearButton by remember { mutableStateOf(mutableListOf<Boolean>(true, true, true, true)) }
    var colorList by remember { mutableStateOf(buttonColor(indexFirstVal, indexSign)) }
    var loopDisplay by remember { mutableStateOf(0..1) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.topic24),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(15.dp))
//number row
        for (r in 0..1) {
            if (r == 0) {
                loopDisplay = 0..1
            } else {
                loopDisplay = 2..3
            }
            Row(
                modifier = Modifier.width(300.dp).height(100.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (i in loopDisplay) {
                    if (appearButton[i]) {
                        Button(
                            onClick = {
                                listNumber = calculateList(num1, num2, num3, num4, indexFirstVal, indexSign, i) as MutableList<Int>
                                indexFirstVal = listNumber[4]
                                indexSign = listNumber[5]
                                if (i == 0) {
                                    num1 = listNumber[i]
                                } else if (i == 1) {
                                    num2 = listNumber[i]
                                } else if (i == 2) {
                                    num3 = listNumber[i]
                                } else if (i == 3) {
                                    num4 = listNumber[i]
                                }
                                if (listNumber[6] != -1) {
                                    appearButton[listNumber[6]] = false
                                }
                                colorList = buttonColor(indexFirstVal, indexSign)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorList[i]),
                            modifier = Modifier.height(100.dp).width(100.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.number24, listNumber[i]),
                                fontSize = 50.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(15.dp))
//sign row
        Row(
            modifier = Modifier.width(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (i in 4..7) {
                Button(
                    onClick = {
                        listNumber = calculateList(num1, num2, num3, num4, indexFirstVal, indexSign, i
                        ) as MutableList<Int>
                        indexSign = listNumber[5]
                        colorList = buttonColor(indexFirstVal, indexSign)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorList[i]),
                    modifier = Modifier.height(70.dp).width(70.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = stringResource(signList[i - 4]),
                        fontSize = 30.sp
                    )
                }
            }
        }

//  Result
        if (check24(appearButton, listNumber) != "") {
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.result, check24(appearButton, listNumber)),
                fontSize = 30.sp
            )
        } else {
            Spacer(Modifier.height(50.dp))
        }

//  Button
        if (check24(appearButton, listNumber) == "lose") {
            Row(
                modifier = Modifier.width(400.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(onClick = {
                    num1 = listOriginalNumber[0]; num2 = listOriginalNumber[1]; num3 = listOriginalNumber[2]; num4 = listOriginalNumber[3];
                    indexFirstVal = -1; indexSign = -1;
                    listNumber = mutableListOf<Int>(num1, num2, num3, num4, indexFirstVal, indexSign)
                    appearButton = mutableListOf<Boolean>(true, true, true, true)
                    colorList = buttonColor(indexFirstVal, indexSign)
                }) {
                    Text(
                        text = stringResource(R.string.retry),
                        fontSize = 25.sp
                    )
                }
                Button(onClick = {
                    randomNumber = List(4) { Random.nextInt(1, 9) }
                    listOriginalNumber = randomNumber
                    num1 = randomNumber[0]; num2 = randomNumber[1]; num3 = randomNumber[2]; num4 = randomNumber[3];
                    indexFirstVal = -1; indexSign = -1;
                    listNumber = mutableListOf<Int>(num1, num2, num3, num4, indexFirstVal, indexSign)
                    appearButton = mutableListOf<Boolean>(true, true, true, true)
                    colorList = buttonColor(indexFirstVal, indexSign)
                }) {
                    Text(
                        text = stringResource(R.string.incorrect),
                        fontSize = 25.sp
                    )
                }

            }
        } else {
            Button(onClick = {
                randomNumber = List(4) { Random.nextInt(1, 9) }
                listOriginalNumber = randomNumber
                num1 = randomNumber[0]; num2 = randomNumber[1]; num3 = randomNumber[2]; num4 = randomNumber[3];
                indexFirstVal = -1; indexSign = -1;
                listNumber = mutableListOf<Int>(num1, num2, num3, num4, indexFirstVal, indexSign)
                appearButton = mutableListOf<Boolean>(true, true, true, true)
                colorList = buttonColor(indexFirstVal, indexSign)
            }) {
                if (check24(appearButton, listNumber) == "win") {
                    Text(
                        text = stringResource(R.string.correct),
                        fontSize = 25.sp
                    )
                }
                else {
                    Text(
                        text = stringResource(R.string.random_again),
                        fontSize = 25.sp,
                    )
                }
            }
        }

        val context = LocalContext.current
        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Home")
        }
    }
}