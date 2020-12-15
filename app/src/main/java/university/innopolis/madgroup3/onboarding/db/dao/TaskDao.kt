package university.innopolis.madgroup3.onboarding.db.dao

import androidx.room.*
import university.innopolis.madgroup3.onboarding.db.entities.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE checklist_id = :checklistId")
    fun getByChecklistId(checklistId: Int): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Int): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg tasks: TaskEntity)
}