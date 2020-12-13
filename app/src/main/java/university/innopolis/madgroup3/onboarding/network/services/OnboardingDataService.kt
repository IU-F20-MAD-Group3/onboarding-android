package university.innopolis.madgroup3.onboarding.network.services

import retrofit2.Call
import retrofit2.http.GET
import university.innopolis.madgroup3.onboarding.models.Checklist

interface OnboardingDataService {
    @GET("checklists/")
    fun getAllChecklists(): Call<List<Checklist>>
}