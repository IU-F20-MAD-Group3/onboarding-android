package university.innopolis.madgroup3.onboarding.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.activities.MainActivity

class ChecklistsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checklists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: remove the following debugging code
        val checklistRepository = (activity as MainActivity).checklistRepository
        val taskRepository = (activity as MainActivity).taskRepository
        val newsRepository = (activity as MainActivity).newsRepository

        val checklists = checklistRepository.getAllChecklists()
        if (checklists == null) {
            Log.e("ChecklistsFragment", "Failed fetching checklists")
            return
        }
        Log.i("ChecklistsFragment", checklists.toString())

        val checklist = checklistRepository.getChecklist(1)
        if (checklist == null) {
            Log.e("ChecklistsFragment", "Failed fetching a checklist")
            return
        }
        Log.i("ChecklistsFragment", checklist.toString())

        val tasks = taskRepository.getTasksByChecklistId(1)
        if (tasks == null) {
            Log.e("ChecklistsFragment", "Failed fetching tasks")
            return
        }
        Log.i("ChecklistsFragment", tasks.toString())

        val allNews = newsRepository.getAllNews()
        if (allNews == null) {
            Log.e("ChecklistsFragment", "Failed fetching news")
            return
        }
        Log.i("ChecklistsFragment", allNews.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChecklistsFragment()
    }
}