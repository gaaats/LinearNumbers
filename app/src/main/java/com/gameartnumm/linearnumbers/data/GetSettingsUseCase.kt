package com.gameartnumm.linearnumbers.data

class GetSettingsUseCase(private val gameNumbersRepository: GameNumbersRepository) {
    operator fun invoke (difficulty: Difficulty): GameSettings{
        return gameNumbersRepository.getSettings(difficulty)
    }
}