package university.innopolis.madgroup3.onboarding.db.dao

import androidx.room.*
import university.innopolis.madgroup3.onboarding.db.entities.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getById(id: Int): NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg news: NewsEntity)

    @Delete
    fun delete(vararg news: NewsEntity)
}