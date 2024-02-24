package com.example.moviestore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestore.MovieAdapter
import com.example.moviestore.R
import com.example.moviestore.base.SharePreferencesUtils
import com.example.moviestore.models.Movie
import com.example.moviestore.services.MovieApiInterface
import com.example.moviestore.services.MovieApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FavFragment : Fragment() {

    private var mRcvMovies: RecyclerView? = null
    private var mMovieAdapter: MovieAdapter? = null
    private var mMenu: Menu? = null
    private var mListMovies: MutableList<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav, container, false)
        mRcvMovies = view.findViewById(R.id.fm_Movie)
        mRcvMovies!!.layoutManager = LinearLayoutManager(context)
        mListMovies = mutableListOf()
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_search) {
            println("noooooo")
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)
        mMenu = menu
        val search: MenuItem? = menu.findItem(R.id.nav_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search something!"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    println(query)
                    searchMovieByKeyword(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onResume() {
        super.onResume()
        getMovieData()
    }

    @SuppressLint("CheckResult")
    private fun getMovieData() {
        mListMovies?.clear()
        val favList = SharePreferencesUtils(context).favMovieList
        for (movieId in favList) {
            val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
            apiService.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movieResponse ->
                        movieResponse.isFavorite = true
                        mListMovies?.add(movieResponse)
                        mRcvMovies?.adapter =
                            context?.let { mListMovies?.let { list -> MovieAdapter(it, list) } }
                    },
                    {
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    private fun searchMovieByKeyword(keyword: String) {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.searchByKeyword(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    mRcvMovies?.adapter = context?.let { MovieAdapter(it, movieResponse.movies) }
                },
                {
                }
            )
    }
}
