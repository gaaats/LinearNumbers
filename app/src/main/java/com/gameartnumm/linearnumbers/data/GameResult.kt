package com.gameartnumm.linearnumbers.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameResult(
    val winnerOrNot: Boolean,
    val countOfCorrectAns: Int,
    val totalNumberOfAns: Int,
    val gameSettings: GameSettings
): Parcelable