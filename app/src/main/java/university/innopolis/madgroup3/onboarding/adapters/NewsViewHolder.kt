package university.innopolis.madgroup3.onboarding.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import university.innopolis.madgroup3.onboarding.data.models.News

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(nItem: News) {
        itemView.item_news_title.text = nItem.title
        itemView.item_news_text.text = nItem.text
    }
}