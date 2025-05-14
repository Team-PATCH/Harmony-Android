package com.teampatch.daily.certify

sealed class DailyCertifyInfoEvent {
    data class OnMissonChanged(val value: String) : DailyCertifyInfoEvent()
    data class OnTimeChanged(val value: String) : DailyCertifyInfoEvent()
    data object OnCompleteClicked : DailyCertifyInfoEvent()
}