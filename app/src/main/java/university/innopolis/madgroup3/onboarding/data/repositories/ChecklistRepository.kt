package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Checklist
import university.innopolis.madgroup3.onboarding.data.models.Task
import university.innopolis.madgroup3.onboarding.db.dao.ChecklistDao
import university.innopolis.madgroup3.onboarding.db.entities.ChecklistEntity
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

/**
 * Object responsible for managing checklists' local and remote storage.
 */
class ChecklistRepository @Inject constructor(
    private val dataService: OnboardingDataService,
    private val checklistDao: ChecklistDao,
) {

    fun getAllChecklists(): List<Checklist>? {
        val checklistsResponse = dataService.getAllChecklists()
            .subscribeOn(Schedulers.io())
            .map {
                // Cache checklists in the database
                insertMultipleChecklistsInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                // If failed loading checklists, retrieve them from the database
                val checklists = fetchAllChecklistsFromDb()
                ResponseWrapper(checklists, it)
            }
            .blockingGet()

        return checklistsResponse.response
    }

    private fun insertMultipleChecklistsInDb(checklists: List<Checklist>) {
        val checklistEntities = checklists.map { mapChecklistToEntity(it) }
        checklistDao.insertMultiple(*checklistEntities.toTypedArray())
    }

    private fun fetchAllChecklistsFromDb(): List<Checklist> {
        val checklistEntities = checklistDao.getAll()
        return checklistEntities.map { mapEntityToChecklist(it) }
    }

    private fun mapChecklistToEntity(checklist: Checklist) =
        ChecklistEntity(
            id = checklist.id,
            name = checklist.name,
            description = checklist.description,
        )

    private fun mapEntityToChecklist(entity: ChecklistEntity) =
        Checklist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
        )

    fun getChecklist(id: Int): Checklist? {
        val checklistResponse = dataService.getChecklist(id)
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return checklistResponse.response
    }

    fun getChecklistTokens(id: Int): List<Task>? {
        val tokensResponse = dataService.getChecklistTasks(id)
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return tokensResponse.response
    }
}