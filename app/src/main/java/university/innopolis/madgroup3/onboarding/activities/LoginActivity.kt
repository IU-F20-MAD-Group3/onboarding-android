package university.innopolis.madgroup3.onboarding.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject lateinit var tokenRepository: TokenRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val inputUsername = login_username.text.toString()
            val inputPw = login_pw.text.toString()

            val errorText = validateCredentials(inputUsername, inputPw)
            if (!errorText.isNullOrBlank()) {
                val errorToast = Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                errorToast.show()
            } else {
                val token = tokenRepository.requestToken(inputUsername, inputPw)
                token ?: showTokenFailToast()

                // go back to CaptionActivity that decides to proceed to main app
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun validateCredentials(username: String, pw: String): String? {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pw)) {
            return ENTER_CREDENTIALS
        }

        if (username.length < 4) {
            return VALID_USERNAME
        }

        if (pw.length < 6) {
            return SHORT_PW
        }

        return null
    }

    private fun showTokenFailToast() {
        Toast.makeText(
            this,
            "Failed fetching a token",
            Toast.LENGTH_SHORT,
        ).show()
    }

    companion object {
        const val ENTER_CREDENTIALS = "Please enter your credentials."
        const val VALID_USERNAME = "Please enter a valid username."
        const val SHORT_PW = "Please enter a password with more than 6 characters."
    }
}