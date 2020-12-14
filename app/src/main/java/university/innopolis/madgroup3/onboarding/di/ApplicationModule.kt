package university.innopolis.madgroup3.onboarding.di

import android.content.Context
import dagger.Module
import dagger.Provides
import university.innopolis.madgroup3.onboarding.OnboardingApplication

/**
 * Dagger module that provides application-related objects.
 */
@Module
class ApplicationModule {
    @Provides
    fun provideContext(): Context = OnboardingApplication.INSTANCE
}