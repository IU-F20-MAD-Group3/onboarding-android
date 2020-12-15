package university.innopolis.madgroup3.onboarding.data.repositories

import android.util.Log
import io.reactivex.schedulers.Schedulers
import university.innopolis.madgroup3.onboarding.data.models.News
import university.innopolis.madgroup3.onboarding.db.dao.NewsDao
import university.innopolis.madgroup3.onboarding.db.entities.NewsEntity
import university.innopolis.madgroup3.onboarding.network.responses.ResponseWrapper
import university.innopolis.madgroup3.onboarding.network.services.OnboardingDataService
import javax.inject.Inject

/**
 * Object responsible for managing news' local and remote storage.
 */
class NewsRepository @Inject constructor(
    private val dataService: OnboardingDataService,
    private val newsDao: NewsDao,
) {

    fun getAllNews(): List<News>? {
        val newsResponse = dataService.getAllNews()
            .subscribeOn(Schedulers.io())
            .map {
                // Cache news in the database
                insertMultipleNewsInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("NewsRepository", it.message ?: "")
                // If failed loading news, retrieve them from the database
                val allNews = fetchAllNewsFromDb()
                ResponseWrapper(allNews, it)
            }
            .blockingGet()

        return newsResponse.response
    }

    fun getSingleNews(id: Int): News? {
        val newsResponse = dataService.getSingleNews(id)
            .subscribeOn(Schedulers.io())
            .map {
                // Cache news in the database
                insertNewsInDb(it)  // TODO: wrap into a coroutine
                ResponseWrapper(it, null)
            }
            .onErrorReturn {
                Log.e("NewsRepository", it.message ?: "")
                // If failed loading news, retrieve it from the database
                val news = fetchNewsFromDb(id)
                ResponseWrapper(news, it)
            }
            .blockingGet()

        return newsResponse.response
    }

    private fun insertNewsInDb(news: News) {
        val newsEntity = mapNewsToEntity(news)
        newsDao.insert(newsEntity)
    }

    private fun fetchNewsFromDb(id: Int): News {
        val newsEntity = newsDao.getById(id)
        return mapEntityToNews(newsEntity)
    }

    private fun insertMultipleNewsInDb(news: List<News>) {
        val newsEntities = news.map { mapNewsToEntity(it) }
        newsDao.insert(*newsEntities.toTypedArray())
    }

    private fun fetchAllNewsFromDb(): List<News> {
        val newsEntities = newsDao.getAll()
        return newsEntities.map { mapEntityToNews(it) }
    }

    private fun mapNewsToEntity(news: News) =
        NewsEntity(
            id = news.id,
            title = news.title,
            text = news.text,
        )

    private fun mapEntityToNews(entity: NewsEntity) =
        News(
            id = entity.id,
            title = entity.title,
            text = entity.text,
        )
}