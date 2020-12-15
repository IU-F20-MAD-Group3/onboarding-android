package university.innopolis.madgroup3.onboarding.db

import androidx.room.Database
import androidx.room.RoomDatabase
import university.innopolis.madgroup3.onboarding.db.dao.ChecklistDao
import university.innopolis.madgroup3.onboarding.db.dao.TaskDao
import university.innopolis.madgroup3.onboarding.db.entities.ChecklistEntity
import university.innopolis.madgroup3.onboarding.db.entities.TaskEntity

/**
 * Main application database.
 */
@Database(
    entities = [ChecklistEntity::class, TaskEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun checklistDao(): ChecklistDao
    abstract fun taskDao(): TaskDao
}