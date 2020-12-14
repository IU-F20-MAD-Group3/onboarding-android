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
        val repository = (activity as MainActivity).checklistRepository
        val checklists = repository.getAllChecklists()

        if (checklists == null) {
            Log.e("ChecklistsFragment", "Failed fetching checklists")
            return
        }

        Log.i("ChecklistsFragment", checklists.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChecklistsFragment()
    }
}