package university.innopolis.madgroup3.onboarding.network.services

import io.reactivex.Single
import retrofit2.http.GET
import university.innopolis.madgroup3.onboarding.data.models.Checklist

interface OnboardingDataService {
    @GET("checklists/")
    fun getAllChecklists(): Single<List<Checklist>>
}