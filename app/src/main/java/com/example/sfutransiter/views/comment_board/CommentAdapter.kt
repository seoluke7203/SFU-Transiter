package com.example.sfutransiter.views.comment_board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.model.BusStopReview
import com.example.sfutransiter.model.User

class CommentAdapter(commentInterface : CommentBoard.CommentInterface) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var myInterface = commentInterface
    private var myComments = ArrayList<BusStopReview.Response>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent,false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentItem = myComments.get(position)
        holder.commentText.text = commentItem.comment
        holder.commentUser.text = commentItem.author.userName

        if(commentItem.author.rn == User.getCurrentUser().userRn) {
            holder.commentItemList.setOnClickListener {
                myInterface.swapToEditComment(commentItem.routeNo,commentItem.stopReviewRn)
            }
        }
    }

    override fun getItemCount(): Int {
        return myComments.size
    }

    fun replaceList(newList: BusStopReview.ResponseList){
        myComments = newList.list
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val commentText: TextView = itemView.findViewById(R.id.txtComments)
        val commentUser : TextView = itemView.findViewById(R.id.txtAuthor)
        val commentItemList : ConstraintLayout = itemView.findViewById(R.id.commentItem)
    }
}