package com.example.sfutransiter.views.comment_board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentCommentBoardBinding
import com.example.sfutransiter.model.BusStopReview
import com.example.sfutransiter.model.view_model.BusReviewViewModel
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.UserViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.views.MainFragment
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"

class CommentBoard : Fragment() {
    private var _binding: FragmentCommentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var listComments : LiveData<Response<BusStopReview.ResponseList>>
    private lateinit var awsViewModel : BusReviewViewModel
    private lateinit var userViewModel : UserViewModel
    private lateinit var adapterComment : CommentAdapter
    private lateinit var routeId: String
    private lateinit var commentInterface : CommentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommentInterface)
            commentInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement RegisterInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getString(ARG_ROUTE_ID)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBoardBinding.inflate(inflater, container, false)

        binding.txtBusTitle3.text = routeId

        val awsRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val awsViewModelFactory = MyViewModelFactory(awsRepo)
        awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]

        val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val userViewModelFactory = MyViewModelFactory(userRepo)
        userViewModel =
            ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

        val commentRecycler : RecyclerView = binding.root.findViewById(R.id.commentList)
        adapterComment = CommentAdapter(commentInterface)
        commentRecycler.adapter = adapterComment

        listComments = awsViewModel.listBusStopReviews(routeId)
        listComments.observe(viewLifecycleOwner) {
            it.body()?.let { it1 -> adapterComment.replaceList(it1) }
            adapterComment.notifyDataSetChanged()
        }

        binding.button2.setOnClickListener {
            onClickAddComment()
        }

        return binding.root
    }

    private fun onClickAddComment() {
        commentInterface.swapToAddComment(routeId)
    }

    interface CommentInterface {
        fun swapToAddComment(routeId: String)
        fun swapToEditComment(routeId : String, stopRn : String)
    }

    companion object {
        @JvmStatic
        fun newInstance(routeId: String) =
            CommentBoard().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUTE_ID, routeId)
                }
            }

        val TAG: String = CommentBoard::class.java.simpleName
    }
}