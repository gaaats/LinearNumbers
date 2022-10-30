package com.gameartnumm.linearnumbers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameartnumm.linearnumbers.data.Difficulty

class GameVievModelFactory(private val application: Application, private val difficulty: Difficulty): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameVievModel::class.java)){
            return GameVievModel(application, difficulty) as T
        }
        else{
            throw RuntimeException("there is no such class")
        }
    }
}