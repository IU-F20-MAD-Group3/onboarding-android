package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Checklist
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

    fun getChecklist(id: Int): Checklist? {
        val checklistResponse = dataService.getChecklist(id)
            .subscribeOn(Schedulers.io())
            .map {
                // Cache checklist in the database
                insertChecklistInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                // If failed loading checklist, retrieve it from the database
                val checklist = fetchChecklistFromDb(id)
                ResponseWrapper(checklist, it)
            }
            .blockingGet()

        return checklistResponse.response
    }

    private fun insertChecklistInDb(checklist: Checklist) {
        val checklistEntity = mapChecklistToEntity(checklist)
        checklistDao.insert(checklistEntity)
    }

    private fun fetchChecklistFromDb(id: Int): Checklist {
        val checklistEntity = checklistDao.getById(id)
        return mapEntityToChecklist(checklistEntity)
    }

    private fun insertMultipleChecklistsInDb(checklists: List<Checklist>) {
        val checklistEntities = checklists.map { mapChecklistToEntity(it) }
        checklistDao.insert(*checklistEntities.toTypedArray())
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
}