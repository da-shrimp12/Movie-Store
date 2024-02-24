package com.example.moviestore

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.base.Constants
import com.example.moviestore.base.SharePreferencesUtils
import com.example.moviestore.models.Movie
import com.example.moviestore.services.MovieApiInterface
import com.example.moviestore.services.MovieApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {


    private lateinit var mTextReleaseDate: TextView
    private lateinit var mTextRating: TextView
    private lateinit var mImgMovie: ImageView
    private lateinit var mTextOverview: TextView
    private lateinit var mRecyclerViewCastCrew: RecyclerView

    private lateinit var mMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViews()
        initData()
        btn_reminder.setOnClickListener {
            val intent = Intent(this,AlarmActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        mTextReleaseDate = findViewById(R.id.movie_release_date)
        mTextRating = findViewById(R.id.rating)
        mImgMovie = findViewById(R.id.img_movie)
        mTextOverview = findViewById(R.id.text_overview)
        mRecyclerViewCastCrew = findViewById(R.id.rc_cast)
    }

    private fun initData() {

        mMovie = intent.getParcelableExtra(OBJECT_KEY)!!
        title = mMovie.title
        mTextReleaseDate.text = mMovie.release
        mTextRating.text = mMovie.vote
        Glide.with(applicationContext)
            .load(Constants.IMAGE_BASE + mMovie.poster)
            .into(mImgMovie)
        mTextOverview.text = mMovie.overview
        mMovie.id?.let { getCastData(it) }
    }

    @SuppressLint("CheckResult")
    private fun getCastData(movieId: String) {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getCastAndCrew(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    mRecyclerViewCastCrew.adapter =
                        applicationContext?.let { CastCrewAdapter(it, movieResponse.castcrews) }
                },
                {
                }
            )
    }

    companion object {
        const val OBJECT_KEY: String = "ObjectKey"
    }

}