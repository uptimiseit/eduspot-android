package com.dw.eduspot.di

import com.dw.eduspot.data.fake.FakeSessionRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSessionRepository(): FakeSessionRepository {
        return FakeSessionRepository()
    }
}