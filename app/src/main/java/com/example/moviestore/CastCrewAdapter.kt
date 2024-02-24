package com.example.moviestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.base.Constants
import com.example.moviestore.models.CastCrew

class CastCrewAdapter(
    private val mContext: Context,
    private val castCrewList: List<CastCrew>
) : RecyclerView.Adapter<CastCrewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img)
        var textName: TextView = itemView.findViewById(R.id.text_name)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cast_crew, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val castCrew = castCrewList[position]
        if (castCrew == null) return
        viewHolder.textName.text = castCrew.name
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE +  castCrew.profilePath)
            .into(viewHolder.image)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = castCrewList.size
}