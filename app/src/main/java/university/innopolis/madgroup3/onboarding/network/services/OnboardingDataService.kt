package university.innopolis.madgroup3.onboarding.network.services

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import university.innopolis.madgroup3.onboarding.data.models.Checklist
import university.innopolis.madgroup3.onboarding.data.models.News
import university.innopolis.madgroup3.onboarding.data.models.Task

interface OnboardingDataService {

    @GET("checklists/")
    fun getAllChecklists(): Single<List<Checklist>>

    @GET("checklists/{id}/")
    fun getChecklist(@Path("id") id: Int): Single<Checklist>

    @GET("checklists/{id}/tasks/")
    fun getChecklistTasks(@Path("id") id: Int): Single<List<Task>>

    @GET("tasks/{id}/")
    fun getTask(@Path("id") id: Int): Single<Task>

    @POST("tasks/{id}/finish/")
    fun finishTask(@Path("id") id: Int): Completable

    @GET("news/")
    fun getAllNews(): Single<List<News>>

    @GET("news/{id}/")
    fun getSingleNews(@Path("id") id: Int): Single<News>
}