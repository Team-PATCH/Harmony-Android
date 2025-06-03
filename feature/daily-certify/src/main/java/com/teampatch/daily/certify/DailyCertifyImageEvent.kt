package com.teampatch.daily.certify

sealed class DailyCertifyImageEvent {
//    data object OnImageSelected(val uri: Uri) : DailyCertifyImageEvent()
    data object OnNextClicked : DailyCertifyImageEvent()
}