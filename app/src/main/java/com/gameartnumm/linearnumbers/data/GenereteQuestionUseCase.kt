package com.gameartnumm.linearnumbers.data

class GenereteQuestionUseCase(private val gameNumbersRepository: GameNumbersRepository) {

    fun gen (maxSum: Int):Question {
        return gameNumbersRepository.genereteQuestion(maxSum, COUNT_OF_VARINTS_FOR_ANSWEAR)
    }

//    operator fun invoke(maxSum: Int): Question {
//        return gameNumbersRepository.genereteQuestion(maxSum, COUNT_OF_VARINTS_FOR_ANSWEAR)
//    }

    companion object{
        private const val COUNT_OF_VARINTS_FOR_ANSWEAR = 6
    }
}