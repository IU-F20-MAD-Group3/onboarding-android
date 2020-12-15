package university.innopolis.madgroup3.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.models.News

class NewsItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfNews = listOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as NewsViewHolder
        vh.bindView(listOfNews[position])
    }

    override fun getItemCount(): Int = listOfNews.size

    fun setNewsList(listOfNews: List<News>) {
        this.listOfNews = listOfNews
        notifyDataSetChanged()
    }
}