package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Task
import university.innopolis.madgroup3.onboarding.db.dao.TaskDao
import university.innopolis.madgroup3.onboarding.db.entities.TaskEntity
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

/**
 * Object responsible for managing tasks' local and remote storage.
 */
class TaskRepository @Inject constructor(
    private val dataService: OnboardingDataService,
    private val taskDao: TaskDao,
) {

    fun getTasksByChecklistId(checklistId: Int): List<Task>? {
        val tokensResponse = dataService.getChecklistTasks(checklistId)
            .subscribeOn(Schedulers.io())
            .map {
                // Cache tasks in the database
                insertMultipleTokensInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("ChecklistsRepository", it.message ?: "")
                // If failed loading tasks, retrieve them from the database
                val tasks = fetchTasksByChecklistIdFromDb(checklistId)
                ResponseWrapper(tasks, it)
            }
            .blockingGet()

        return tokensResponse.response
    }

    fun getTask(id: Int): Task? {
        val taskResponse = dataService.getTask(id)
            .subscribeOn(Schedulers.io())
            .map {
                // Cache task in the database
                insertTaskInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("TaskRepository", it.message ?: "")
                // If failed loading task, retrieve it from the database
                val task = fetchTaskFromDb(id)
                ResponseWrapper(task, it)
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

    private fun insertTaskInDb(task: Task) {
        val taskEntity = mapTaskToEntity(task)
        taskDao.insert(taskEntity)
    }

    private fun fetchTaskFromDb(id: Int): Task {
        val taskEntity = taskDao.getById(id)
        return mapEntityToTask(taskEntity)
    }

    private fun insertMultipleTokensInDb(tasks: List<Task>) {
        val taskEntities = tasks.map { mapTaskToEntity(it) }
        taskDao.insert(*taskEntities.toTypedArray())
    }

    private fun fetchTasksByChecklistIdFromDb(checklistId: Int): List<Task> {
        val taskEntities = taskDao.getByChecklistId(checklistId)
        return taskEntities.map { mapEntityToTask(it) }
    }

    private fun mapTaskToEntity(task: Task) =
        TaskEntity(
            id = task.id,
            checklistId = task.checklistId,
            name = task.name,
            description = task.description,
            status = task.status,
        )

    private fun mapEntityToTask(entity: TaskEntity) =
        Task(
            id = entity.id,
            checklistId = entity.checklistId,
            name = entity.name,
            description = entity.description,
            status = entity.status,
        )
}