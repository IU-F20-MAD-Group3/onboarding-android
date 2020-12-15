package university.innopolis.madgroup3.onboarding.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = ChecklistEntity::class,
            parentColumns = ["id"],
            childColumns = ["checklist_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class TaskEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "checklist_id") var checklistId: Int,
    val name: String,
    val description: String,
    val status: String,
)
