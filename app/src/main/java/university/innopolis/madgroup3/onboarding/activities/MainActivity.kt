package university.innopolis.madgroup3.onboarding.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import university.innopolis.madgroup3.onboarding.data.repositories.TaskRepository
import university.innopolis.madgroup3.onboarding.fragments.ChecklistsFragment
import university.innopolis.madgroup3.onboarding.fragments.NewsFragment
import university.innopolis.madgroup3.onboarding.fragments.TasksFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var checklistRepository: ChecklistRepository
    @Inject lateinit var taskRepository: TaskRepository

    private lateinit var mTasksFragment: TasksFragment
    private lateinit var mChecklistsFragment: ChecklistsFragment
    private lateinit var mNewsFragment: NewsFragment

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_tasks -> {
                    supportActionBar?.title = getString(R.string.tasks)
                    inflateFragment(mTasksFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_checklists -> {
                    supportActionBar?.title = getString(R.string.checklists)
                    inflateFragment(mChecklistsFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_news -> {
                    supportActionBar?.title = getString(R.string.news)
                    inflateFragment(mNewsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun bind() {
        mTasksFragment = TasksFragment.newInstance()
        mChecklistsFragment = ChecklistsFragment.newInstance()
        mNewsFragment = NewsFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as OnboardingApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        bind()

        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_navigation.findViewById<View>(R.id.action_tasks).performClick()
    }
}