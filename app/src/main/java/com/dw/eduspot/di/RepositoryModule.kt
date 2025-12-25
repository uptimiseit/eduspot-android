package com.dw.eduspot.di


import com.dw.eduspot.data.local.db.dao.CourseDao
import com.dw.eduspot.data.remote.api.CourseApi
import com.dw.eduspot.data.repository.CourseRepository
import com.dw.eduspot.data.repository.CourseRepositoryImpl
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
    fun provideCourseRepository(
        api: CourseApi,
        dao: CourseDao
    ): CourseRepository =
        CourseRepositoryImpl(api, dao)
}