package university.innopolis.madgroup3.onboarding.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import university.innopolis.madgroup3.onboarding.OnboardingApplication
import university.innopolis.madgroup3.onboarding.R
import university.innopolis.madgroup3.onboarding.activities.MainActivity
import university.innopolis.madgroup3.onboarding.adapters.NewsItemAdapter
import university.innopolis.madgroup3.onboarding.data.repositories.ChecklistRepository
import university.innopolis.madgroup3.onboarding.data.repositories.NewsRepository
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject lateinit var newsRepository: NewsRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.applicationContext as OnboardingApplication).appComponent.inject(this)

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsItemAdapter()

        fragment_news_recycler.layoutManager = LinearLayoutManager(context)
        fragment_news_recycler.adapter = adapter

        val news = newsRepository.getAllNews()
        // TODO: delete logs
        println(news.toString())
        if (news != null) {
            (fragment_news_recycler.adapter as NewsItemAdapter).setNewsList(news)
        } else {
            // TODO: handle null news response
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment()
    }
}