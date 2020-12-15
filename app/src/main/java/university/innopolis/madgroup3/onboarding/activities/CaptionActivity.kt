package university.innopolis.madgroup3.onboarding.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import javax.inject.Inject

class CaptionActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var checklistRepository: ChecklistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as OnboardingApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)

        if (tokenRepository.getToken() == null) {
            startActivityForResult(
                Intent(this, LoginActivity::class.java),
                LoginActivity.REQUEST_CODE
            )
        } else {
            startMainActivity()
            finish()
        }
    }

    private fun startMainActivity() = startActivity(Intent(this, MainActivity::class.java))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            startMainActivity()
            finish()
        } else {
            finish()
        }
    }
}