package university.innopolis.madgroup3.onboarding.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_checklists.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.activities.MainActivity
import university.innopolis.madgroup3.onboarding.adapters.ChecklistsItemAdapter
import university.innopolis.madgroup3.onboarding.data.models.Checklist
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import javax.inject.Inject
import javax.inject.Named

class ChecklistsFragment : Fragment(), ChecklistsItemAdapter.OnItemClickListener {

    @Inject
    lateinit var checklistRepository: ChecklistRepository

    @Inject
    @Named("secure")
    lateinit var secureSharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.applicationContext as OnboardingApplication).appComponent.inject(this)

        return inflater.inflate(R.layout.fragment_checklists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChecklistsItemAdapter()

        fragment_checklists_recycler.layoutManager = LinearLayoutManager(context)
        fragment_checklists_recycler.adapter = adapter

        (fragment_checklists_recycler.adapter as ChecklistsItemAdapter).setOnItemClickListener(this)

        val checklists = checklistRepository.getAllChecklists()
        if (checklists != null) {
            (fragment_checklists_recycler.adapter as ChecklistsItemAdapter).setChecklistsList(
                checklists
            )
        } else {
            showToast()
        }

        fragment_checklists_swipe.setOnRefreshListener {
            fragment_checklists_swipe.isRefreshing = true
            val newChecklists = checklistRepository.getAllChecklists()
            if (newChecklists != null) {
                (fragment_checklists_recycler.adapter as ChecklistsItemAdapter).setChecklistsList(
                    newChecklists
                )
            } else {
                showToast()
            }
            fragment_checklists_swipe.isRefreshing = false
        }
    }

    private fun showToast() {
        val message = "Unable to fetch checklists. Please try again or contact the admin"
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChecklistsFragment()
    }

    override fun onItemClick(checklist: Checklist) {
        (activity as MainActivity).apply {
            mTasksFragment =
                TasksFragment.newInstance(checklistId = checklist.id)
            main_navigation.findViewById<View>(R.id.action_tasks).performClick()
        }

        secureSharedPreferences.edit().putInt("CURRENT_CHECKLIST", checklist.id).apply()
    }
}