package university.innopolis.madgroup3.onboarding.di

import dagger.Component
import university.innopolis.madgroup3.onboarding.activities.CaptionActivity
import university.innopolis.madgroup3.onboarding.activities.LoginActivity
import javax.inject.Singleton

/**
 * Definition of the Application graph.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        SecureSharedPreferencesModule::class,
    ]
)
interface ApplicationComponent {
    fun inject(activity: CaptionActivity)
    fun inject(activity: LoginActivity)
}