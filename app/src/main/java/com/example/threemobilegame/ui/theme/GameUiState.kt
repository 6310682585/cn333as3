package com.example.threemobilegame.ui.theme

import com.example.threemobilegame.data.Question

data class GameUiState(
    val currentQuestion: Question = Question("", listOf("", "", "", ""), ""),
    val currentQuestionCount: Int = 1,
    val score: Int = 0,
    val isGameOver: Boolean = false
)