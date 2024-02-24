package com.example.movies.base
class Constants{
    companion object {
        const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        const val API_KEY = "e7631ffcb8e766993e5ec0c1f4245f93"

        val CATEGORY_OPTIONS = arrayOf("Popular", "Top rate", "Up coming", "Now playing")
        val SORT_BY_OPTIONS = arrayOf("Release Date", "Rating")

        const val FILTER_CATEGORY_KEY = "RATE_CATEGORY_KEY"
        const val FILTER_FROM_RATE_KEY = "RATE_FROM_RATE_KEY"
        const val FILTER_FROM_YEAR_KEY = "RATE_FROM_YEAR_KEY"
        const val SORT_BY_KEY = "SORT_BY_KEY"

    }
}
