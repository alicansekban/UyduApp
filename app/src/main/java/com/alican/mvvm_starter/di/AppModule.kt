package com.alican.mvvm_starter.di

import android.content.Context
import androidx.room.Room
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.SatelliteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {
        @Provides
        fun provideChannelDao(appDatabase: AppDatabase): SatelliteDao {
            return appDatabase.satelliteDao()
        }
    }

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "satellite.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
