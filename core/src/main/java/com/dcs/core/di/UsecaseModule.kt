package com.dcs.core.di

import com.dcs.core.domain.usecase.ClearScoreUsecase
import com.dcs.core.domain.usecase.ClearScoreUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsecaseModule {

    @Binds
    @Singleton
    abstract fun bindClearScoreUsecase(
        impl: ClearScoreUsecaseImpl
    ): ClearScoreUsecase
}