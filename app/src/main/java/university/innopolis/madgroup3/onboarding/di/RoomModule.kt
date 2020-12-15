package university.innopolis.madgroup3.onboarding.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import university.innopolis.madgroup3.onboarding.db.AppDatabase
import university.innopolis.madgroup3.onboarding.db.dao.ChecklistDao
import university.innopolis.madgroup3.onboarding.db.dao.NewsDao
import university.innopolis.madgroup3.onboarding.db.dao.TaskDao
import javax.inject.Singleton

/**
 * Dagger module that provides an application database and its DAOs.
 */
@Module
class RoomModule {

    companion object {
        const val DATABASE_NAME = "onboarding-db"
    }

    @Singleton
    @Provides
    fun provideAppDatabase(appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun provideChecklistDao(db: AppDatabase): ChecklistDao = db.checklistDao()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideNewsDao(db: AppDatabase): NewsDao = db.newsDao()
}