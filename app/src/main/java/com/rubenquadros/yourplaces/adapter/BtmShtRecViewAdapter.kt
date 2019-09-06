package com.rubenquadros.yourplaces.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rubenquadros.yourplaces.R
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.PlacesResponse
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.SearchPlacesResponse
import com.rubenquadros.yourplaces.utils.ApplicationUtility

class BtmShtRecViewAdapter(private val placesResponse: PlacesResponse?, private val searchPlacesResponse: SearchPlacesResponse?,
                           private val dbResponse: List<PlacesEntity>?): RecyclerView.Adapter<BtmShtRecViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.places_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(placesResponse == null && dbResponse == null) {
            searchPlacesResponse?.response?.venues!!.size
        }else if(searchPlacesResponse == null && placesResponse == null) {
            dbResponse!!.size
        }else {
            placesResponse?.response?.groups?.get(0)?.items!!.size
        }
//        return if(placesResponse == null) {
//            searchPlacesResponse?.response?.venues!!.size
//        }else {
//            placesResponse.response?.groups?.get(0)?.items!!.size
//        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(placesResponse == null && dbResponse == null) {
            holder.nameTv?.text = searchPlacesResponse?.response?.venues?.get(position)?.name
            holder.categoryTv?.text = searchPlacesResponse?.response?.venues?.get(position)?.categories?.get(0)?.name
            holder.addressTv?.text = ApplicationUtility.formatList(searchPlacesResponse?.response?.venues?.get(position)?.location?.formattedAddress!!)
        }else if(searchPlacesResponse == null && dbResponse == null) {
            holder.nameTv?.text = placesResponse?.response?.groups?.get(0)?.items?.get(position)?.venue?.name
            holder.categoryTv?.text = placesResponse?.response?.groups?.get(0)?.items?.get(position)?.venue?.categories?.get(0)?.name
            holder.addressTv?.text = ApplicationUtility.formatList(placesResponse?.response?.groups?.get(0)?.items?.get(position)?.venue?.location?.formattedAddress!!)
        }else {
            holder.nameTv?.text = dbResponse?.get(position)?.name
            holder.categoryTv?.text = dbResponse?.get(position)?.category
            holder.addressTv?.text = dbResponse?.get(position)?.address
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView? = itemView.findViewById(R.id.nameTv)
        val categoryTv: TextView? = itemView.findViewById(R.id.categoryTv)
        val addressTv: TextView? = itemView.findViewById(R.id.addressTv)
    }
}