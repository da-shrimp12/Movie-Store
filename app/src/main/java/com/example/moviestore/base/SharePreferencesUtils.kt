package com.example.moviestore.base

import android.content.SharedPreferences
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresApi
import android.os.Build
import java.util.*

class SharePreferencesUtils(context: Context?) {
    private val mSharedPreferences: SharedPreferences =
        context!!.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    @SuppressLint("MutatingSharedPrefs")
    fun addFavorMovies(id: String?) {
        var favorMovies = mSharedPreferences.getString(FAV_MOVIE_LIST_KEY, "")
        if (favorMovies!!.isEmpty()) favorMovies = id else favorMovies += ",$id"
        val editor = mSharedPreferences.edit()
        editor.remove(FAV_MOVIE_LIST_KEY)
        editor.putString(FAV_MOVIE_LIST_KEY, favorMovies)
        editor.apply()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MutatingSharedPrefs")
    fun removeFavorMovies(id: String?) {
        val favorMovies = mSharedPreferences.getString(FAV_MOVIE_LIST_KEY, "")
        if (favorMovies!!.isEmpty()) return
        val setId: MutableSet<String> = HashSet()
        for (movieId in favorMovies.split(",").toTypedArray()) {
            setId.add(movieId)
        }
        setId.remove(id)
        val newList = java.lang.String.join(",", setId)
        val editor = mSharedPreferences.edit()
        editor.remove(FAV_MOVIE_LIST_KEY)
        editor.putString(FAV_MOVIE_LIST_KEY, newList)
        editor.apply()
    }

    fun getStringByKey(key: String?): String? {
        return mSharedPreferences.getString(key, null)
    }

    fun setStringByKey(key: String?, value: String?) {
        val editor = mSharedPreferences.edit()
        editor.remove(key)
        editor.putString(key, value)
        editor.apply()
    }

    fun isFavor(id: String?): Boolean {
        val favorMovies = mSharedPreferences.getString(FAV_MOVIE_LIST_KEY, "")
        return favorMovies!!.contains(id!!)
    }

    val favMovieList: List<String>
        get() {
            val favorMovies = mSharedPreferences.getString(FAV_MOVIE_LIST_KEY, "")
            return listOf(*favorMovies!!.split(",").toTypedArray().clone())
        }

    companion object {
        private const val SHARED_PREFERENCES_FILE_NAME = "AppPreferences"
        private const val FAV_MOVIE_LIST_KEY = "FAVOR_MOVIE"
    }

}