package university.innopolis.madgroup3.onboarding.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.models.Checklist
import university.innopolis.madgroup3.onboarding.models.Token
import university.innopolis.madgroup3.onboarding.network.TokenManager
import university.innopolis.madgroup3.onboarding.network.interceptors.AuthInterceptor
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import java.util.concurrent.TimeUnit

class CaptionActivity : AppCompatActivity() {
    companion object {
        const val SECURE_SHARED_PREFS_KEY = "secure_preferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)

        // TODO: remove test queries
        val masterKey = MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                this,
                SECURE_SHARED_PREFS_KEY,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

        val tokenManager = TokenManager(sharedPreferences)

        // TODO: PUT YOUR TOKEN HERE
        tokenManager.saveToken(Token(""))

        val authInterceptor = AuthInterceptor(tokenManager)

        val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://onboarding.intropy.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val dataService = retrofit.create(OnboardingDataService::class.java)
        dataService.getAllChecklists().enqueue(object : Callback<List<Checklist>> {
            override fun onResponse(
                    call: Call<List<Checklist>>,
                    response: Response<List<Checklist>>,
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                            this@CaptionActivity,
                            "Failed fetching checklists",
                            Toast.LENGTH_SHORT,
                    ).show()
                }

                val checklists = response.body()
                if (checklists == null) {
                    Log.e("OnChecklistsResponse", "Failed to interpret checklists data")
                    return
                }

                Log.i("Checklists", checklists.toString())
            }

            override fun onFailure(call: Call<List<Checklist>>, t: Throwable) {
                Toast.makeText(
                        this@CaptionActivity,
                        "Failed fetching data from the API",
                        Toast.LENGTH_SHORT,
                ).show()
            }
        })
    }
}