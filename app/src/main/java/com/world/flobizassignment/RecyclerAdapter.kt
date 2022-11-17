package com.world.flobizassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.world.flobizassignment.Model.Item
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(val context: Context, private var arr: List<Item>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun filterList(filterList: ArrayList<Item>) {
        arr = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arr[position]
        Picasso.get().load(data.owner.profileImage).into(holder.ownerImage)
        holder.ownerName.text = data.owner.displayName
        holder.title.text = data.title
        holder.postedDate.text = getDate(data.creationDate.toLong() , "dd-MM-yyyy")

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var ownerImage : CircleImageView = view.findViewById(R.id.owner_image)
        var title : AppCompatTextView = view.findViewById(R.id.title)
        var ownerName : AppCompatTextView = view.findViewById(R.id.owner_name)
        var postedDate : AppCompatTextView = view.findViewById(R.id.posted_date)
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}

