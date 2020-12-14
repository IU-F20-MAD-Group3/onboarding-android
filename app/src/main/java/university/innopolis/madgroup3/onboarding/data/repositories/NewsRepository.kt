package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.News
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val dataService: OnboardingDataService) {

    fun getAllNews(): List<News>? {
        val newsResponse = dataService.getAllNews()
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("NewsRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return newsResponse.response
    }

    fun getSingleNews(id: Int): News? {
        val newsResponse = dataService.getSingleNews(id)
            .subscribeOn(Schedulers.io())
            .map { ResponseWrapper(it, null) }
            .onErrorReturn {
                Log.e("NewsRepository", it.message ?: "")
                ResponseWrapper(null, it)
            }
            .blockingGet()

        return newsResponse.response
    }
}