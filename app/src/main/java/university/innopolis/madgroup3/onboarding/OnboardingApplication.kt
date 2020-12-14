package university.innopolis.madgroup3.onboarding

import android.app.Application
import university.innopolis.madgroup3.onboarding.di.DaggerApplicationComponent

class OnboardingApplication : Application() {
    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: OnboardingApplication
            private set
    }
}