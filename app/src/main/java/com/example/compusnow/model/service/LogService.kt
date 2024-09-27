package com.example.compusnow.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}