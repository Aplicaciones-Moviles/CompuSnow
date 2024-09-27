package com.example.compusnow.model.service.module

import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.ConfigurationService
import com.example.compusnow.model.service.LogService
import com.example.compusnow.model.service.StorageService
import com.example.compusnow.model.service.impl.AccountServiceImpl
import com.example.compusnow.model.service.impl.ConfigurationServiceImpl
import com.example.compusnow.model.service.impl.LogServiceImpl
import com.example.compusnow.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    @Singleton
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    @Singleton
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    @Singleton
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}
