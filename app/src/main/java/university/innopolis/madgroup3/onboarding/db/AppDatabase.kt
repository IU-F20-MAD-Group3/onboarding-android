package university.innopolis.madgroup3.onboarding.db

import androidx.room.Database
import androidx.room.RoomDatabase
import university.innopolis.madgroup3.onboarding.db.dao.ChecklistDao
import university.innopolis.madgroup3.onboarding.db.entities.ChecklistEntity

/**
 * Main application database.
 */
@Database(entities = arrayOf(ChecklistEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checklistDao(): ChecklistDao
}