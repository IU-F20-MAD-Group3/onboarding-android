package university.innopolis.madgroup3.onboarding.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.data.repositories.TokenRepository
import university.innopolis.madgroup3.onboarding.fragments.ChecklistsFragment
import university.innopolis.madgroup3.onboarding.fragments.NewsFragment
import university.innopolis.madgroup3.onboarding.fragments.TasksFragment
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    @Named("secure")
    lateinit var secureSharedPreferences: SharedPreferences

    lateinit var mTasksFragment: TasksFragment
    lateinit var mChecklistsFragment: ChecklistsFragment
    lateinit var mNewsFragment: NewsFragment

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

    fun inflateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            commit()
        }
    }

    private fun bind() {
        val currentChecklistId = secureSharedPreferences.getInt("CURRENT_CHECKLIST", -1)

        mTasksFragment =
            TasksFragment.newInstance(if (currentChecklistId != -1) currentChecklistId else null)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_menu_logout -> {
                tokenRepository.deleteToken()
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}