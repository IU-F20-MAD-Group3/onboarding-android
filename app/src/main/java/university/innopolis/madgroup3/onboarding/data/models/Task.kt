package university.innopolis.madgroup3.onboarding.data.models

import com.google.gson.annotations.SerializedName

data class Task(
    val id: Int,
    @SerializedName("checklist_id")
    val checklistId: Int,
    val name: String,
    val description: String,
    val status: String,
)
