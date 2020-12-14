package university.innopolis.madgroup3.onboarding.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import university.innopolis.madgroup3.onboarding.activities.CaptionActivity
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger module that provides secure shared preferences object and master key.
 */
@Module
class SecureSharedPreferencesModule {

    companion object {
        const val SECURE_SHARED_PREFS_KEY = "secure_preferences"
    }

    @Singleton
    @Provides
    @Named("secure")
    fun provideSecureSharedPreferences(context: Context, masterKey: MasterKey): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            SECURE_SHARED_PREFS_KEY,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Singleton
    @Provides
    fun provideMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
}