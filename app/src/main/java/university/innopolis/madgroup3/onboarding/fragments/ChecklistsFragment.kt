package university.innopolis.madgroup3.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_checklists.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.adapters.ChecklistsItemAdapter
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import javax.inject.Inject

class ChecklistsFragment : Fragment() {

    @Inject
    lateinit var checklistRepository: ChecklistRepository

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
}