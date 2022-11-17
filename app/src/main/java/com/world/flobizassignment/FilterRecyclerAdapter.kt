package com.world.flobizassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class FilterRecyclerAdapter(val context: Context,
                            var listenerInterface: OnRecyclerViewItemClick, private var arr: List<String>) : RecyclerView.Adapter<FilterRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_filter_recycler_layout, parent, false)
        return ViewHolder(view ,  listenerInterface)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arr[position]

        holder.filterText.text = data

    }


    class ViewHolder(itemView: View, listenerInterface: OnRecyclerViewItemClick) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var filterText : TextView = itemView.findViewById(R.id.filter_text)
        var cardData  : CardView = itemView.findViewById(R.id.filter_parent)

        private var mOnNoteListener: OnRecyclerViewItemClick = listenerInterface
        override fun onClick(view: View) {

            mOnNoteListener.onItemClick(adapterPosition)
        }

        init {
            cardData.setOnClickListener(this)
        }
    }

    interface OnRecyclerViewItemClick {
        fun onItemClick( position: Int)
    }
}

