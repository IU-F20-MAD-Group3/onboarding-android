package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Checklist
import university.innopolis.madgroup3.onboarding.data.models.Task
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

class ChecklistRepository @Inject constructor(private val dataService: OnboardingDataService) {

    fun getAllChecklists(): List<Checklist>? {
        val checklistsResponse = dataService.getAllChecklists()
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return checklistsResponse.response
    }

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