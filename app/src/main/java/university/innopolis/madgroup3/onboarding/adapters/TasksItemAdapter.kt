package university.innopolis.madgroup3.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.models.Task

class TasksItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfTasks = listOf<Task>()

    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as TasksViewHolder
        vh.bindView(listOfTasks[position], this.onItemClickListener)
    }

    override fun getItemCount(): Int = listOfTasks.size

    fun setTasksList(listOfTasks: List<Task>) {
        this.listOfTasks = listOfTasks
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
    }

}