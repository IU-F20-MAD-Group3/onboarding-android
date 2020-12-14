package university.innopolis.madgroup3.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.models.Checklist


class ChecklistsItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfChecklists = listOf<Checklist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChecklistsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ChecklistsViewHolder
        vh.bindView(listOfChecklists[position])
    }

    override fun getItemCount(): Int = listOfChecklists.size

    fun setChecklistsList(listOfChecklists: List<Checklist>) {
        this.listOfChecklists = listOfChecklists
        notifyDataSetChanged()
    }
}