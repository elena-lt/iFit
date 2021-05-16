package com.example.ifit.models

sealed class TimerEvent {

    object START: TimerEvent()
    object STOP: TimerEvent()
}