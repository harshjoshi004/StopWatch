package com.harshjoshi.stopwatch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.Duration
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.time.toKotlinDuration

class MainViewModel: ViewModel() {
    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var seconds = mutableStateOf("00")
    var minutes = mutableStateOf("00")
    var hours = mutableStateOf("00")
    var isPlaying = mutableStateOf(false)

    fun start(){
        timer = fixedRateTimer(
            initialDelay = 1000L,
            period = 1000L
        ){
            //the guy used seconds instead of ofSeconds
            //I am using java.time.Duration while he is using kotlin.time.Duration
            time = time.plus(Duration.ofSeconds(1))
            updateTimeStates()
        }
        isPlaying.value = true
    }

    private fun updateTimeStates(){
        time.toKotlinDuration().toComponents { hours, minutes, seconds, _ ->
            this@MainViewModel.seconds.value = seconds.pad()
            this@MainViewModel.minutes.value = minutes.pad()
            this@MainViewModel.hours.value = hours.pad()
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2,'0')
    }
    private fun Long.pad(): String {
        return this.toString().padStart(2,'0')
    }

    fun pause(){
        timer.cancel()
        isPlaying.value = false

    }

    fun stop(){
        pause()
        time = Duration.ZERO
        updateTimeStates()
    }
}