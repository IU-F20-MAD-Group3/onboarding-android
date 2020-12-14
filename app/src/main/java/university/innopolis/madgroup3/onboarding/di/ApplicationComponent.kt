package university.innopolis.madgroup3.onboarding.di

import androidx.fragment.app.Fragment
import dagger.Component
import university.innopolis.madgroup3.onboarding.activities.CaptionActivity
import university.innopolis.madgroup3.onboarding.activities.LoginActivity
import university.innopolis.madgroup3.onboarding.activities.MainActivity
import university.innopolis.madgroup3.onboarding.fragments.NewsFragment
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
    fun inject(activity: MainActivity)
    fun inject(fragment: NewsFragment)
}