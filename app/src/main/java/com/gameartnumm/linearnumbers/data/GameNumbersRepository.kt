package com.gameartnumm.linearnumbers.data

interface GameNumbersRepository {
    fun genereteQuestion(maxSum: Int, countOfVarAnswears: Int): Question
    fun getSettings(levelOfDifficulty: Difficulty): GameSettings
}