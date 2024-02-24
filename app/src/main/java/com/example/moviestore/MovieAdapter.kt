package com.example.moviestore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.base.Constants
import com.example.moviestore.base.SharePreferencesUtils
import com.example.moviestore.models.Movie
import kotlinx.android.synthetic.main.item_grid.view.*
import kotlinx.android.synthetic.main.item_list.view.*


class MovieAdapter(
    private val mContext: Context,
    private val movies: List<Movie>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val movie = movies[position]
        return movie.typeDisplay
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnFavor = view.findViewById<ImageButton>(R.id.favouriteBtnPA)!!;
        fun bindMovie(movie: Movie) {
            itemView.movie_title.text = movie.title
            itemView.movie_release_date.text = movie.release
            itemView.movie_overview.text = movie.overview
            itemView.movie_rating.text = movie.vote
            Glide.with(itemView).load(Constants.IMAGE_BASE + movie.poster).into(itemView.movie_poster)
        }
    }

    class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindMovieGrid(movie: Movie) {
            itemView.movie_title_grid.text = movie.title
            Glide.with(itemView).load(Constants.IMAGE_BASE + movie.poster).into(itemView.movie_poster_grid)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (viewType) {
            Movie.TYPE_LIST -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
                return ListViewHolder(view!!)
            }
            Movie.TYPE_GRID -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
                return GridViewHolder(view!!)
            }
        }
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext.applicationContext, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.OBJECT_KEY, movies[position])
            mContext.startActivity(intent)
        }
        if (itemType == Movie.TYPE_LIST) {
            val viewListHolder = holder as ListViewHolder
            viewListHolder.bindMovie(movies[position])

            if (movies[position].isFavorite) {
                viewListHolder.btnFavor.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                viewListHolder.btnFavor.setImageResource(R.drawable.star_icon)
            }

            viewListHolder.btnFavor.setOnClickListener {
                val movie = movies[position]
                if (movie.isFavorite) {
                    movie.isFavorite = false;
                    SharePreferencesUtils(mContext).removeFavorMovies(movie.id)
                    viewListHolder.btnFavor.setImageResource(R.drawable.star_icon)
                } else {
                    movie.isFavorite = true;
                    SharePreferencesUtils(mContext).addFavorMovies(movie.id)
                    viewListHolder.btnFavor.setImageResource(R.drawable.ic_baseline_star_24)
                }
                notifyDataSetChanged()
            }
        } else if (itemType == Movie.TYPE_GRID) {
            val viewGridHolder = holder as GridViewHolder
            viewGridHolder.bindMovieGrid(movies[position])
        }
    }
}