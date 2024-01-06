package com.test.wheatherapp.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.wheatherapp.MainApplication
import com.test.wheatherapp.R
import com.test.wheatherapp.data.WeatherItemData
import com.test.wheatherapp.utils.Utils

class MainRecycler : RecyclerView.Adapter<MainRecycler.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<WeatherItemData>() {
        override fun areItemsTheSame(oldItem: WeatherItemData, newItem: WeatherItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherItemData, newItem: WeatherItemData): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<WeatherItemData>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_image)
        val title: TextView = view.findViewById(R.id.item_title)
        val sender: TextView = view.findViewById(R.id.item_sender_name)
        val startDate: TextView = view.findViewById(R.id.item_start_date_body)
        val endDate: TextView = view.findViewById(R.id.item_end_date_body)
        val duration: TextView = view.findViewById(R.id.item_duration_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_alert, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]

        val imgUrl = String.format(
            MainApplication.getContext().getString(R.string.link_img_url),
            item.imageId,
            Utils.DEFAULT_PIC_DIMEN,
            Utils.DEFAULT_PIC_DIMEN
        )

        Picasso.get()
            .load(imgUrl)
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .into(holder.image)

        holder.title.text = item.name
        holder.sender.text = item.sender
        holder.startDate.text = item.getStartedString()
        holder.endDate.text = item.getEndedString()
        holder.duration.text = item.getDurationString()
    }


}