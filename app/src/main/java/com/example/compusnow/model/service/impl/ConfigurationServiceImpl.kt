package com.example.compusnow.model.service.impl

import com.example.compusnow.BuildConfig
import com.example.compusnow.model.service.ConfigurationService
import javax.inject.Inject

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {

    // Método de ejemplo para obtener una configuración simple
    override suspend fun fetchConfiguration(): Boolean {
        // Dado que no estamos usando RemoteConfig en este momento,
        // se puede retornar un valor simple o simulado
        return BuildConfig.DEBUG
    }

    companion object {
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}
