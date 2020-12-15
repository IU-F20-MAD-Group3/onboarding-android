package university.innopolis.madgroup3.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.activities.MainActivity
import university.innopolis.madgroup3.onboarding.adapters.TasksItemAdapter
import university.innopolis.madgroup3.onboarding.data.models.Task
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import university.innopolis.madgroup3.onboarding.data.repositories.TaskRepository
import javax.inject.Inject

class TasksFragment : Fragment(), TasksItemAdapter.OnItemClickListener {

    @Inject
    lateinit var checklistRepository: ChecklistRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    private var checklistId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            checklistId = it.getInt(CHECKLIST_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.applicationContext as OnboardingApplication).appComponent.inject(this)

        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checklistId == null) {
            (activity as MainActivity).main_navigation.findViewById<View>(R.id.action_checklists)
                .performClick()
            return
        }

        val checklist = checklistRepository.getChecklist(checklistId!!)
        fragment_tasks_title.text = checklist?.name
        fragment_tasks_description.text = checklist?.description

        val adapter = TasksItemAdapter()
        fragment_tasks_recycler.layoutManager = LinearLayoutManager(context)
        fragment_tasks_recycler.adapter = adapter

        (fragment_tasks_recycler.adapter as TasksItemAdapter).setOnItemClickListener(this)

        val tasks = taskRepository.getTasksByChecklistId(checklistId!!)
        if (tasks != null) {
            (fragment_tasks_recycler.adapter as TasksItemAdapter).setTasksList(
                tasks
            )
        } else {
            showToast()
        }

        fragment_tasks_swipe.setOnRefreshListener {
            fragment_tasks_swipe.isRefreshing = true
            val newTasks = taskRepository.getTasksByChecklistId(checklistId!!)
            if (!newTasks.isNullOrEmpty()) {
                (fragment_tasks_recycler.adapter as TasksItemAdapter).setTasksList(
                    newTasks
                )
            } else {
                showToast()
            }
            fragment_tasks_swipe.isRefreshing = false
        }
    }

    private fun showToast() {
        val message = "Unable to fetch tasks. Please try again or contact the admin"
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }

    companion object {
        private const val CHECKLIST_PARAM = "checklist"

        @JvmStatic
        fun newInstance(checklistId: Int?): TasksFragment {
            if (checklistId == null) return TasksFragment()
            return TasksFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHECKLIST_PARAM, checklistId)
                }
            }
        }
    }

    override fun onItemClick(task: Task) {
        taskRepository.finishTask(task.id)
    }
}