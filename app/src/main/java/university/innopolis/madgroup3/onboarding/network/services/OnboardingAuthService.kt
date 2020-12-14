package university.innopolis.madgroup3.onboarding.network.services

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import university.innopolis.madgroup3.onboarding.data.models.Token
import university.innopolis.madgroup3.onboarding.network.requests.TokenRequest

interface OnboardingAuthService {
    @POST("tokens/")
    fun getAuthToken(@Body body: TokenRequest): Single<Token>
}