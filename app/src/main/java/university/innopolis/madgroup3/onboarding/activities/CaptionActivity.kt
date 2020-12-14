package university.innopolis.madgroup3.onboarding.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import javax.inject.Inject

class CaptionActivity : AppCompatActivity() {

    @Inject lateinit var tokenRepository: TokenRepository
    @Inject lateinit var checklistRepository: ChecklistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as OnboardingApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)

        // TODO: remove test queries
        testAPI()
    }

    private fun testAPI() {
        val username = "root"
        val password = "dnNeZHZediYrY28oNnkoMmRqJXJla"

        val token = tokenRepository.requestToken(username, password)
        token ?: return showTokenFailToast()

        val checklists = checklistRepository.getAllChecklists()
        checklists ?: return showChecklistsFailToast()

        Log.i("CaptionActivity", checklists.toString())
    }

    private fun showTokenFailToast() {
        Toast.makeText(
            this,
            "Failed fetching a token",
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun showChecklistsFailToast() {
        Toast.makeText(
            this,
            "Failed fetching checklists",
            Toast.LENGTH_SHORT,
        ).show()
    }
}