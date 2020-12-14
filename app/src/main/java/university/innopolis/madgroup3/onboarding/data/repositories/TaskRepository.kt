package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Task
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

class TaskRepository @Inject constructor(private val dataService: OnboardingDataService) {

    fun getTask(id: Int): Task? {
        val taskResponse = dataService.getTask(id)
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("TaskRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return taskResponse.response
    }

    fun finishTask(id: Int) {
        val error = dataService.finishTask(id)
            .subscribeOn(Schedulers.io())
            .blockingGet()

        if (error != null) {
            Log.e("TaskRepository", error.message ?: "")
        }
    }
}