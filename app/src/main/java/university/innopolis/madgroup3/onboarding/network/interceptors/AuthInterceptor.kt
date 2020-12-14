package university.innopolis.madgroup3.onboarding.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import javax.inject.Inject

/**
 * Interceptor that adds token to the request
 */
class AuthInterceptor @Inject constructor(private val repository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        repository.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Token ${it.token}")
        }

        return chain.proceed(requestBuilder.build())
    }
}