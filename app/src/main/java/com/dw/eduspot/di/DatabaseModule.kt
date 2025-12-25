package com.dw.eduspot.di

import android.content.Context
import androidx.room.Room
import com.dw.eduspot.data.local.db.EduSpotDatabase
import com.dw.eduspot.data.local.db.dao.CourseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): EduSpotDatabase =
        Room.databaseBuilder(
            context,
            EduSpotDatabase::class.java,
            "eduspot.db"
        ).build()

    @Provides
    @Singleton
    fun provideCourseDao(db: EduSpotDatabase): CourseDao =
        db.courseDao()
}