package com.gameartnumm.linearnumbers.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfCorrectAns: Int,
    val minPercentOfCorrectAns: Int,
    val gameTimeISSeconds: Int
):Parcelable