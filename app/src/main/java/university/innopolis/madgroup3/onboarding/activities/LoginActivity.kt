package university.innopolis.madgroup3.onboarding.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenRepository: TokenRepository

    private val mOnClickListener = View.OnClickListener {
        val inputUsername = login_username.text.toString()
        val inputPw = login_pw.text.toString()

        val errorText = validateCredentials(inputUsername, inputPw)

        if (!errorText.isNullOrBlank()) {
            val errorToast = Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
            errorToast.show()
            return@OnClickListener
        }

        login_btn.isEnabled = false
        val token = tokenRepository.requestToken(inputUsername, inputPw)
        login_btn.isEnabled = true

        if (token == null) {
            showToast("Failed logging in, try again or contact your admin")
            login_pw.setText("")
            return@OnClickListener
        }

        setResult(RESULT_OK)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as OnboardingApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener(mOnClickListener)
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

    companion object {
        const val ENTER_CREDENTIALS = "Please enter your credentials."
        const val VALID_USERNAME = "Please enter a valid username."
        const val SHORT_PW = "Please enter a password with more than 6 characters."

        const val REQUEST_CODE = 1337
    }
}