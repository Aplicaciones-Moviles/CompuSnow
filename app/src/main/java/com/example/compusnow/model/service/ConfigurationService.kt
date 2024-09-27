package com.example.compusnow.model.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
}