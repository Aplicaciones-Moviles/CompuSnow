package com.example.compusnow.model.service.impl

import com.example.compusnow.model.service.LogService
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {

    // Método para registrar un error no fatal
    override fun logNonFatalCrash(throwable: Throwable) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.recordException(throwable)
    }
}
