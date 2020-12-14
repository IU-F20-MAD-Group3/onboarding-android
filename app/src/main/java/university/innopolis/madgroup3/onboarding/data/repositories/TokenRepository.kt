package university.innopolis.madgroup3.onboarding.data.repositories

import android.content.SharedPreferences
import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.Token
import university.innopolis.madgroup3.onboarding.network.requests.TokenRequest
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingAuthService
import javax.inject.Inject
import javax.inject.Named

/**
 * Object that manages a token via local and remote storage.
 */
class TokenRepository @Inject constructor(
    @Named("secure") private val secureSharedPreferences: SharedPreferences,
    private val authService: OnboardingAuthService,
) {

    companion object {
        const val AUTH_TOKEN_KEY = "auth_token"
    }

    private fun saveToken(token: Token) {
        with(secureSharedPreferences.edit()) {
            putString(AUTH_TOKEN_KEY, token.token)
            apply()
        }
    }

    fun getToken(): Token? {
        val tokenStr = secureSharedPreferences.getString(AUTH_TOKEN_KEY, null)
        tokenStr ?: return null
        return Token(tokenStr)
    }

    fun requestToken(username: String, password: String): Token? {
        val request = TokenRequest(username, password)

        val tokenResponse = authService.getAuthToken(request)
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("TokenRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        val token = tokenResponse.response
        if (token != null) saveToken(token)

        return token
    }
}