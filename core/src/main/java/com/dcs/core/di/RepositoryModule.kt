package com.dcs.core.di

import com.dcs.core.data.repository.ClearScoreRepositoryImpl
import com.dcs.core.domain.repository.ClearScoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindClearScoreRepository(
        impl: ClearScoreRepositoryImpl
    ): ClearScoreRepository
}

//@Module
//@InstallIn(SingletonComponent::class)
//class RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideClearScoreRepository(
//        impl: ClearScoreRepositoryImpl
//    ): ClearScoreRepository = impl
//}