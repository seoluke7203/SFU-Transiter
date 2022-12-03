package com.example.sfutransiter.views.comment_board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.model.BusStopReview

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    //TODO wait until AWS is back up
    private lateinit var myComments : ArrayList<BusStopReview.Response>
    //private var myComments = BusStopReview.ResponseList(ArrayList())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent,false)

        //TODO remove when AWS is back up
        myComments = ArrayList()

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO wait until AWS is back up
        //val commentItem = myComments.get(position)
        //holder.commentText.text = commentItem.comment
    }

    override fun getItemCount(): Int {
        //TODO wait until AWS is back upa
        //return myComments.size
        return 5
    }

    fun replaceList(newList: BusStopReview.ResponseList){
        myComments = newList.list
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val commentText: TextView = itemView.findViewById(R.id.txtComments)
    }
}