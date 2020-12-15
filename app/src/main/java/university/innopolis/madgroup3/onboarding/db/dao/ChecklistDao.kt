package university.innopolis.madgroup3.onboarding.db.dao

import androidx.room.*
import university.innopolis.madgroup3.onboarding.db.entities.ChecklistEntity

@Dao
interface ChecklistDao {

    @Query("SELECT * FROM checklists")
    fun getAll(): List<ChecklistEntity>

    @Query("SELECT * FROM checklists WHERE id = :id")
    fun getById(id: Int): ChecklistEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg checklists: ChecklistEntity)

    @Delete
    fun delete(vararg checklists: ChecklistEntity)
}