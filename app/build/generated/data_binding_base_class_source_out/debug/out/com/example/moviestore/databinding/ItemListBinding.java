// Generated by view binder compiler. Do not edit!
package com.example.moviestore.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.moviestore.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton favouriteBtnPA;

  @NonNull
  public final ImageView imgAdultRate;

  @NonNull
  public final TextView movieOverview;

  @NonNull
  public final ImageView moviePoster;

  @NonNull
  public final TextView movieRating;

  @NonNull
  public final TextView movieReleaseDate;

  @NonNull
  public final TextView movieTitle;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  private ItemListBinding(@NonNull LinearLayout rootView, @NonNull ImageButton favouriteBtnPA,
      @NonNull ImageView imgAdultRate, @NonNull TextView movieOverview,
      @NonNull ImageView moviePoster, @NonNull TextView movieRating,
      @NonNull TextView movieReleaseDate, @NonNull TextView movieTitle, @NonNull TextView textView3,
      @NonNull TextView textView4, @NonNull TextView textView5) {
    this.rootView = rootView;
    this.favouriteBtnPA = favouriteBtnPA;
    this.imgAdultRate = imgAdultRate;
    this.movieOverview = movieOverview;
    this.moviePoster = moviePoster;
    this.movieRating = movieRating;
    this.movieReleaseDate = movieReleaseDate;
    this.movieTitle = movieTitle;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.favouriteBtnPA;
      ImageButton favouriteBtnPA = ViewBindings.findChildViewById(rootView, id);
      if (favouriteBtnPA == null) {
        break missingId;
      }

      id = R.id.img_adult_rate;
      ImageView imgAdultRate = ViewBindings.findChildViewById(rootView, id);
      if (imgAdultRate == null) {
        break missingId;
      }

      id = R.id.movie_overview;
      TextView movieOverview = ViewBindings.findChildViewById(rootView, id);
      if (movieOverview == null) {
        break missingId;
      }

      id = R.id.movie_poster;
      ImageView moviePoster = ViewBindings.findChildViewById(rootView, id);
      if (moviePoster == null) {
        break missingId;
      }

      id = R.id.movie_rating;
      TextView movieRating = ViewBindings.findChildViewById(rootView, id);
      if (movieRating == null) {
        break missingId;
      }

      id = R.id.movie_release_date;
      TextView movieReleaseDate = ViewBindings.findChildViewById(rootView, id);
      if (movieReleaseDate == null) {
        break missingId;
      }

      id = R.id.movie_title;
      TextView movieTitle = ViewBindings.findChildViewById(rootView, id);
      if (movieTitle == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      return new ItemListBinding((LinearLayout) rootView, favouriteBtnPA, imgAdultRate,
          movieOverview, moviePoster, movieRating, movieReleaseDate, movieTitle, textView3,
          textView4, textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
