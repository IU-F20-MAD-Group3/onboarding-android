package university.innopolis.madgroup3.onboarding.network.interceptors

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import university.innopolis.madgroup3.onboarding.network.TokenManager

/**
 * Interceptor that adds token to the request
 */
class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        tokenManager.fetchToken()?.let {
            requestBuilder.addHeader("Authorization", "Token ${it.token}")
        }

        return chain.proceed(requestBuilder.build())
    }
}