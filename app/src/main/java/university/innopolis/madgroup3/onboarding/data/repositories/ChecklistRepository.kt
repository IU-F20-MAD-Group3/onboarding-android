package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Checklist
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

class ChecklistRepository @Inject constructor(private val dataService: OnboardingDataService) {

    fun getAllChecklists(): List<Checklist>? {
        return dataService.getAllChecklists()
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                Log.e("ChecklistsOnErrorReturn", it.message ?: "")
                null
            }
            .blockingGet()
    }
}