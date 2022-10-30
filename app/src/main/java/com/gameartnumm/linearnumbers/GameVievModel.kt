package com.gameartnumm.linearnumbers

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameartnumm.linearnumbers.data.*

class GameVievModel(private val application: Application, private val currentDifficulty : Difficulty) : ViewModel() {

    private lateinit var currentGameSettings: GameSettings
    private var timer: CountDownTimer? = null

    private val repositoryImpl = GameNumbersRepositoryImpl
    private val genereteQuestionUseCase = GenereteQuestionUseCase(repositoryImpl)
    private val getSettingsUseCase = GetSettingsUseCase(repositoryImpl)

    // LIVEDATA FOR RIGHT ANS
    private val _righthAnsverLD = MutableLiveData<Int>()
    val righthAnsverLD: LiveData<Int>
        get() = _righthAnsverLD

    // LIVEDATA  IS USER ANSVERED ENOUGH COUNT CORRECT ANS OR NOT
    private val _isEnoughNumCorrectAns = MutableLiveData<Boolean>()
    val isEnoughNumCorrectAns: LiveData<Boolean>
        get() = _isEnoughNumCorrectAns

    // LIVEDATA  IS USER ANSVERED ENOUGH PERCENT OF CORRECT ANS OR NOT
    private val _isEnoughPercentCorrectAnsLD = MutableLiveData<Boolean>()
    val isEnoughPercentCorrectAnsLD: LiveData<Boolean>
        get() = _isEnoughPercentCorrectAnsLD

    // LIVEDATA FOR TIMER
    private val _timeInStirngFormatLD = MutableLiveData<String>()
    val timeInStirngFormatLD: LiveData<String>
        get() = _timeInStirngFormatLD

    // LIVEDATA FOR CURRENT QUESTION
    private val _questionLD = MutableLiveData<Question>()
    val questionLD: LiveData<Question>
        get() = _questionLD

    // LIVEDATA FOR PERCENT OF CORRECT ANS
    private val _percentCorrectAnsLD = MutableLiveData<Int>()
    val percentCorrectAnsLD: LiveData<Int>
        get() = _percentCorrectAnsLD

    // LIVEDATA FOR MIN PERCENT OF CORRECT ANS
    private val _minPercentCorrectAnsLD = MutableLiveData<Int>()
    val minPercentCorrectAnsLD: LiveData<Int>
        get() = _minPercentCorrectAnsLD

    // LIVEDATA FOR PERCENT OF CORRECT ANS IN STRING
    private val _percentCorrectAnsInStringLD = MutableLiveData<String>()
    val percentCorrectAnsInStringLD: LiveData<String>
        get() = _percentCorrectAnsInStringLD

    // LIVEDATA FOR GAME RESULT
    private val _gameResultLD = MutableLiveData<GameResult>()
    val gameResultLD: LiveData<GameResult>
        get() = _gameResultLD


    var numberOfCorrectAnsv = 0
    var totalNumberOfAnsv = 0
    var percentOfCorrectAnsv = 0

    init {
        currentGameSettings = getSettingsUseCase(currentDifficulty)
        _minPercentCorrectAnsLD.value = currentGameSettings.minPercentOfCorrectAns

        gameMethod()
    }

    fun gameMethod() {
        generateQuesion()
        startTimer()
    }

    fun takeUserAnsvCheckItUpdProgGenerNextQuest(ans: Int) {
        checkUserAns(ans)
        updateProgress()
        generateQuesion()

    }

    private fun updateProgress() {
        percentOfCorrectAnsv = calculatePercentOfCorrectAns()

        _percentCorrectAnsLD.value = percentOfCorrectAnsv
        _percentCorrectAnsInStringLD.value = String.format(
            application.getString(R.string.progress_answers),
            numberOfCorrectAnsv,
            currentGameSettings.minCountOfCorrectAns)
        val a = numberOfCorrectAnsv >= currentGameSettings.minCountOfCorrectAns
        _isEnoughNumCorrectAns.value = a
        Log.d("color", "${_isEnoughNumCorrectAns.value}")
        _isEnoughPercentCorrectAnsLD.value = percentOfCorrectAnsv >= currentGameSettings.minPercentOfCorrectAns
    }

    private fun calculatePercentOfCorrectAns(): Int {
        if (totalNumberOfAnsv == 0){
            return 0
        }
        return ((numberOfCorrectAnsv / totalNumberOfAnsv.toDouble()) * 100).toInt()
    }


    private fun checkUserAns(number: Int) {
        if (number == righthAnsverLD.value) {
            numberOfCorrectAnsv++
            Log.d("good", "correct")
        }
        totalNumberOfAnsv++
    }


    private fun generateQuesion() {
//        _questionLD.value = genereteQuestionUseCase(currentGameSettings.maxSumValue)
        _questionLD.value = genereteQuestionUseCase.gen(currentGameSettings.maxSumValue)
        Log.d("pppp", "${_questionLD.value}")
        // можливо буде помилка, замінити на звичайну змінну
        _righthAnsverLD.value = questionLD.value?.rightAns
    }

    private fun startTimer() {
        timer = object :
            CountDownTimer(
                currentGameSettings.gameTimeISSeconds * Constance.MILISEC_IN_SECONDS_IN_LONG,
                Constance.MILISEC_IN_SECONDS_IN_LONG
            ) {
            override fun onTick(timeLeft: Long) {
                _timeInStirngFormatLD.value = formatedTimeToStringForLiveData(timeLeft)
            }

            override fun onFinish() {
                generateGameResult()
            }

        }
        timer?.start()
    }

    private fun generateGameResult() {
        val isVin = checkNumberAndPercentOfCorrectAns()
        _gameResultLD.value =  GameResult(isVin, numberOfCorrectAnsv, totalNumberOfAnsv, currentGameSettings)
    }

    private fun checkNumberAndPercentOfCorrectAns(): Boolean {
        if (isEnoughNumCorrectAns.value == null || isEnoughPercentCorrectAnsLD.value == null){
            return false
        }
        val firstCondition = isEnoughNumCorrectAns.value!!
        val secondCondition = isEnoughPercentCorrectAnsLD.value!!
        return firstCondition&&secondCondition
    }

    private fun formatedTimeToStringForLiveData(timeInLong: Long): String {
        val seconds = (timeInLong / Constance.MILISEC_IN_SECONDS_IN_LONG)
        val leftMinutes = seconds / Constance.SECONDS_IN_MINUTE
        val leftSeconds = seconds - (leftMinutes * Constance.SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", leftMinutes, leftSeconds)
    }

    override fun onCleared() {
        timer?.cancel()
        super.onCleared()
    }


}