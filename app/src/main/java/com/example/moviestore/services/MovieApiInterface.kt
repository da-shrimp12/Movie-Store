package com.example.moviestore.services

import com.example.movies.base.Constants.Companion.API_KEY
import com.example.moviestore.models.CastCrewResponse
import com.example.moviestore.models.Movie
import com.example.moviestore.models.MovieResponse
import io.reactivex.Single
import retrofit2.http.*

interface MovieApiInterface {

    @GET("3/movie/popular?api_key=$API_KEY")
    fun getMoviePopularList(): Single<MovieResponse>

    @GET("3/movie/top_rated?api_key=$API_KEY")
    fun getMovieTopRateList(): Single<MovieResponse>

    @GET("3/movie/upcoming?api_key=$API_KEY")
    fun getMovieUpComingList(): Single<MovieResponse>

    @GET("3/movie/now_playing?api_key=$API_KEY")
    fun getMovieNowPlayingList(): Single<MovieResponse>

    @GET("3/movie/popular?api_key=$API_KEY")
    fun getMoviePopularList(
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("3/movie/top_rated?api_key=$API_KEY")
    fun getMovieTopRateList(
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("3/movie/upcoming?api_key=$API_KEY")
    fun getMovieUpComingList(
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("3/movie/now_playing?api_key=$API_KEY")
    fun getMovieNowPlayingList(
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("3/movie/{movieId}?api_key=$API_KEY")
    fun getMovieDetail(
        @Path ("movieId") movieId: String
    ): Single<Movie>

    @GET("3/movie/{movieId}/credits?api_key=$API_KEY")
    fun getCastAndCrew(
        @Path("movieId") movieId: String
    ): Single<CastCrewResponse>

    @GET("3/search/movie?api_key=$API_KEY")
    fun searchByKeyword(
        @Query("query") keyword: String
    ): Single<MovieResponse>


}