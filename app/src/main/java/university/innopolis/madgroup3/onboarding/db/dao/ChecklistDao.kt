package university.innopolis.madgroup3.onboarding.db.dao

import androidx.room.*
import university.innopolis.madgroup3.onboarding.db.entities.ChecklistEntity

@Dao
interface ChecklistDao {

    @Query("SELECT * FROM checklists")
    fun getAll(): List<ChecklistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultiple(vararg checklists: ChecklistEntity)

    @Delete
    fun delete(checklist: ChecklistEntity)

    @Delete
    fun deleteMultiple(vararg checklists: ChecklistEntity)
}