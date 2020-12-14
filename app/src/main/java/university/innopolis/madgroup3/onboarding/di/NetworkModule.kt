package university.innopolis.madgroup3.onboarding.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import university.innopolis.madgroup3.onboarding.BuildConfig
import university.innopolis.madgroup3.onboarding.network.interceptors.AuthInterceptor
import university.innopolis.madgroup3.onboarding.network.services.OnboardingAuthService
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger module that provides objects for connecting to external API.
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("withAuth")
    fun provideHttpClientWithAuth(authInterceptor: AuthInterceptor): OkHttpClient {
        return createHttpClient(true, authInterceptor)
    }

    @Singleton
    @Provides
    @Named("withoutAuth")
    fun provideHttpClientWithoutAuth(): OkHttpClient {
        return createHttpClient(false)
    }

    @Singleton
    @Provides
    @Named("withAuth")
    fun provideRetrofitWithAuth(@Named("withAuth") httpClient: OkHttpClient): Retrofit {
        return createRetrofit(httpClient)
    }

    @Singleton
    @Provides
    @Named("withoutAuth")
    fun provideRetrofitWithoutAuth(@Named("withoutAuth") httpClient: OkHttpClient): Retrofit {
        return createRetrofit(httpClient)
    }

    @Singleton
    @Provides
    fun provideOnboardingDataService(
        @Named("withAuth") retrofit: Retrofit,
    ): OnboardingDataService {
        return retrofit.create(OnboardingDataService::class.java)
    }

    @Singleton
    @Provides
    fun provideOnboardingAuthService(
        @Named("withoutAuth") retrofit: Retrofit,
    ): OnboardingAuthService {
        return retrofit.create(OnboardingAuthService::class.java)
    }

    private fun createHttpClient(
        withAuth: Boolean,
        authInterceptor: AuthInterceptor? = null,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        if (withAuth) {
            authInterceptor!!
            builder.addInterceptor(authInterceptor)
        }

        return builder.build()
    }

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}