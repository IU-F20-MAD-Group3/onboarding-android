package university.innopolis.madgroup3.onboarding.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklists")
data class ChecklistEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
)