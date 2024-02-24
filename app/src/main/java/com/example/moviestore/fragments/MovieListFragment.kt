package com.example.moviestore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movies.base.Constants
import com.example.moviestore.MovieAdapter
import com.example.moviestore.R
import com.example.moviestore.base.SharePreferencesUtils
import com.example.moviestore.models.Movie
import com.example.moviestore.services.MovieApiInterface
import com.example.moviestore.services.MovieApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class MovieListFragment : Fragment() {
    private var mRcvMovies: RecyclerView? = null
    private var mMovieAdapter: MovieAdapter? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mCurrentTypeDisplay = Movie.TYPE_LIST
    private var mCurrentPosition = 0
    private var mMenu: Menu? = null
    private var mListMovies: MutableList<Movie>? = null
    private lateinit var mPullToRefreshLayout: SwipeRefreshLayout
    private var mPage = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        mRcvMovies = view.findViewById(R.id.fm_Movie)
        mGridLayoutManager = GridLayoutManager(this.context, 3)
        mLinearLayoutManager = LinearLayoutManager(this.context)
        mListMovies = mutableListOf()
        mRcvMovies!!.layoutManager = mLinearLayoutManager
        mRcvMovies!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    loadMore()
                    Toast.makeText(context,"Load more done, Slow Scroll again to see more!!",Toast.LENGTH_SHORT).show()
                }
            }
        })
        mPullToRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        mPullToRefreshLayout.setOnRefreshListener {
            getMovieData()
            thread {
                Thread.sleep(2000)
                mPullToRefreshLayout.isRefreshing = false
            }
        }
        setTypeDisplayRecyclerView(Movie.TYPE_LIST)
        setHasOptionsMenu(true)
        getMovieData()
        return view
    }

    private fun setCurrentPosition() {
        val layoutManager = mRcvMovies!!.layoutManager
        when (mCurrentTypeDisplay) {
            Movie.TYPE_GRID -> mCurrentPosition =
                (layoutManager as GridLayoutManager?)!!.findFirstVisibleItemPosition()
            Movie.TYPE_LIST -> mCurrentPosition =
                (layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        mMenu = menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_menu) {
            setCurrentPosition()
            onClickChangeTypeDisplay()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickChangeTypeDisplay() {
        if (mCurrentTypeDisplay == Movie.TYPE_GRID) {
            setTypeDisplayRecyclerView(Movie.TYPE_LIST)
            mRcvMovies!!.layoutManager = mLinearLayoutManager
        } else if (mCurrentTypeDisplay == Movie.TYPE_LIST) {
            setTypeDisplayRecyclerView(Movie.TYPE_GRID)
            mRcvMovies!!.layoutManager = mGridLayoutManager
        }
        mMovieAdapter!!.notifyDataSetChanged()
        setIconMenu()
        mRcvMovies!!.scrollToPosition(mCurrentPosition)
    }

    private fun setTypeDisplayRecyclerView(typeDisplay: Int) {
        if (mListMovies == null || mListMovies!!.isEmpty()) {
            return
        }
        mCurrentTypeDisplay = typeDisplay
        for (movie in mListMovies!!) {
            movie.typeDisplay = typeDisplay
        }
    }

    private fun setIconMenu() {
        when (mCurrentTypeDisplay) {
            Movie.TYPE_GRID -> mMenu!!.getItem(0).setIcon(R.drawable.ic_grid)
            Movie.TYPE_LIST -> mMenu!!.getItem(0).setIcon(R.drawable.ic_list)
        }
    }

    @SuppressLint("CheckResult")
    private fun getMovieData() {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val apiCall =
            when (SharePreferencesUtils(context).getStringByKey(Constants.FILTER_CATEGORY_KEY)) {
                "0" -> apiService.getMoviePopularList()
                "1" -> apiService.getMovieTopRateList()
                "2" -> apiService.getMovieUpComingList()
                "3" -> apiService.getMovieNowPlayingList()
                else -> apiService.getMoviePopularList()
            }
        apiCall
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    val outputMovies = ArrayList<Movie>()

                    val filterRate =
                        SharePreferencesUtils(context).getStringByKey(Constants.FILTER_FROM_RATE_KEY)
                    val filterYear =
                        SharePreferencesUtils(context).getStringByKey(Constants.FILTER_FROM_YEAR_KEY)

                    for (i in movieResponse.movies.indices) {
                        movieResponse.movies[i].isFavorite =
                            SharePreferencesUtils(context).isFavor(movieResponse.movies[i].id)

                        if (filterRate != null && movieResponse.movies[i].vote?.toDouble()!! < filterRate.toDouble()) {
                            continue
                        }
                        val movieYear =
                            movieResponse.movies[i].release?.split("-")?.get(0)?.toInt()
                        if (filterYear != null && movieYear != null && movieYear < filterYear.toInt()) {
                            continue
                        }
                        outputMovies.add(movieResponse.movies[i])
                    }
                    mListMovies?.clear()
                    mListMovies?.addAll(outputMovies)
                    mMovieAdapter = context?.let { mListMovies?.let { list -> MovieAdapter(it, list.toList()) } }
                    mRcvMovies?.adapter = mMovieAdapter
                },
                {
                }
            )
    }
    @SuppressLint("CheckResult")
    private fun loadMore(){
        mPage++
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val apiCall =
            when (SharePreferencesUtils(context).getStringByKey(Constants.FILTER_CATEGORY_KEY)) {
                "0" -> apiService.getMoviePopularList(mPage)
                "1" -> apiService.getMovieTopRateList(mPage)
                "2" -> apiService.getMovieUpComingList(mPage)
                "3" -> apiService.getMovieNowPlayingList(mPage)
                else -> apiService.getMoviePopularList(mPage)
            }
        apiCall
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    val outputMovies = ArrayList<Movie>()

                    val filterRate =
                        SharePreferencesUtils(context).getStringByKey(Constants.FILTER_FROM_RATE_KEY)
                    val filterYear =
                        SharePreferencesUtils(context).getStringByKey(Constants.FILTER_FROM_YEAR_KEY)

                    for (i in movieResponse.movies.indices) {
                        movieResponse.movies[i].isFavorite =
                            SharePreferencesUtils(context).isFavor(movieResponse.movies[i].id)

                        if (filterRate != null && movieResponse.movies[i].vote?.toDouble()!! < filterRate.toDouble()) {
                            continue
                        }
                        val movieYear =
                            movieResponse.movies[i].release?.split("-")?.get(0)?.toInt()
                        if (filterYear != null && movieYear != null && movieYear < filterYear.toInt()) {
                            continue
                        }
                        outputMovies.add(movieResponse.movies[i])
                    }

                    mListMovies?.addAll(outputMovies)
                    mMovieAdapter = context?.let { mListMovies?.let { list -> MovieAdapter(it, list.toList()) } }
                    mRcvMovies?.adapter = mMovieAdapter
                },
                {
                }
            )
    }
}


