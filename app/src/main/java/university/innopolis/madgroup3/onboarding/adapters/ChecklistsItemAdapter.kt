package university.innopolis.madgroup3.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.models.Checklist


class ChecklistsItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfChecklists = listOf<Checklist>()

    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChecklistsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ChecklistsViewHolder
        vh.bindView(listOfChecklists[position], this.onItemClickListener)
    }

    override fun getItemCount(): Int = listOfChecklists.size

    fun setChecklistsList(listOfChecklists: List<Checklist>) {
        this.listOfChecklists = listOfChecklists
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(checklist: Checklist)
    }
}