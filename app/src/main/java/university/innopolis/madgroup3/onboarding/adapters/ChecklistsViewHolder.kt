package university.innopolis.madgroup3.onboarding.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_checklist.view.*
import university.innopolis.madgroup3.onboarding.data.models.Checklist

class ChecklistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(nItem: Checklist) {
        itemView.item_checklist_title.text = nItem.name
        itemView.item_checklist_description.text = nItem.description
    }
}