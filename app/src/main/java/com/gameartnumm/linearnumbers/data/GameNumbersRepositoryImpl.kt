package com.gameartnumm.linearnumbers.data

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameNumbersRepositoryImpl: GameNumbersRepository {

    private const val MIN_NUMBER_FOR_ASKING_VALUE = 4
    private const val MIN_VISIBLE_NUMBER_VALUE = 2


    override fun genereteQuestion(maxSum: Int, countOfVarAnswears: Int): Question {
        val listOfVar = hashSetOf<Int>()
        val sum = Random.nextInt(MIN_NUMBER_FOR_ASKING_VALUE, maxSum+1)
        val visibleAns = Random.nextInt(MIN_VISIBLE_NUMBER_VALUE, sum)
        // ТУТ МОЖЕ БУТИ ПОМИЛКА В !!!ALSO!!!
        val rightAns = (sum - visibleAns).also { listOfVar.add(it) }
        val from = max(rightAns - countOfVarAnswears, MIN_VISIBLE_NUMBER_VALUE)
        val to = min(maxSum, rightAns + countOfVarAnswears)
        while (listOfVar.size < countOfVarAnswears){
            Random.nextInt(from, to).also { listOfVar.add(it) }
        }
        return Question(sum, visibleAns, listOfVar.toList())
    }

    override fun getSettings(levelOfDifficulty: Difficulty): GameSettings {
        return when (levelOfDifficulty){
            Difficulty.TEST -> {
                GameSettings(20,2,50,10)
            }
            Difficulty.EASY -> {
                GameSettings(30,4,60,20)
            }
            Difficulty.NORMAL -> {
                GameSettings(40,5,70,30)
            }
            Difficulty.HARD -> {
                GameSettings(50,6,80,40)
            }
        }
    }
}