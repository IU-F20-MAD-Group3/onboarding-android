package university.innopolis.madgroup3.onboarding.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*
import university.innopolis.madgroup3.onboarding.data.models.Task

class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(nItem: Task, itemClickListener: TasksItemAdapter.OnItemClickListener?) {
        itemView.item_task_title.text = nItem.name
        itemView.item_task_description.text = nItem.description
        itemView.item_task_checkbox.isEnabled = nItem.status == "pending"
        itemView.item_task_checkbox.isChecked = nItem.status != "pending"

        if (nItem.status == "pending") {
            itemView.setOnClickListener {
                itemView.item_task_checkbox.apply {
                    isEnabled = false
                    isChecked = true
                }
                itemClickListener?.onItemClick(nItem)
            }
        }
    }

}