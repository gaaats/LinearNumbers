package com.gameartnumm.linearnumbers.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Difficulty: Parcelable {
    TEST,
    EASY,
    NORMAL,
    HARD
}