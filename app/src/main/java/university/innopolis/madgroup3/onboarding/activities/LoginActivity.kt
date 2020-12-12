package university.innopolis.madgroup3.onboarding.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import university.innopolis.madgroup3.onboarding.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val inputEmail = login_email.text.toString()
            val inputPw = login_pw.text.toString()

            val errorText = validateCredentials(inputEmail, inputPw)
            if (!errorText.isNullOrBlank()) {
                val errorToast = Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT)
                errorToast.show()
            } else {
                // TODO: API call with inputEmail and inputPw
                // TODO: Fire an Intent after successful completion
                // TODO: Fire a Toast on unsuccessful completion (error)
            }
        }
    }

    private fun validateCredentials(email: String, pw: String): String? {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)) {
            return ENTER_CREDENTIALS
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return VALID_EMAIL
        }

        if (pw.length < 6) {
            return SHORT_PW
        }

        return null
    }

    companion object {
        const val ENTER_CREDENTIALS = "Please enter your credentials."
        const val VALID_EMAIL = "Please enter a valid e-mail address."
        const val SHORT_PW = "Please enter a password with more than 6 characters."
    }
}