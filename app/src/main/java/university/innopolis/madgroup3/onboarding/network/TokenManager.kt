package university.innopolis.madgroup3.onboarding.network

import android.content.SharedPreferences
import university.innopolis.madgroup3.onboarding.models.Token

class TokenManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val AUTH_TOKEN_KEY = "auth_token"
    }

    fun saveToken(token: Token) {
        with (sharedPreferences.edit()) {
            putString(AUTH_TOKEN_KEY, token.token)
            apply()
        }
    }

    fun fetchToken(): Token? {
        val tokenStr = sharedPreferences.getString(AUTH_TOKEN_KEY, null) ?: return null
        return Token(tokenStr)
    }
}