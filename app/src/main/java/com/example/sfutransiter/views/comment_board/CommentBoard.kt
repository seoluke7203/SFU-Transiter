package com.example.sfutransiter.views.comment_board

import android.os.Bundle
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
import com.example.sfutransiter.repository.AWSRepo
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"

class CommentBoard : Fragment() {
    private var _binding: FragmentCommentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var listComments : LiveData<Response<BusStopReview.ResponseList>>

    private lateinit var routeId: String

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
        val awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]

        val commentRecycler : RecyclerView = binding.root.findViewById(R.id.commentList)
        val adapterComment = CommentAdapter()
        commentRecycler.adapter = adapterComment

        listComments = awsViewModel.listBusStopReviews(routeId)
        listComments.observe(viewLifecycleOwner) {
            it.body()?.let { it1 -> adapterComment.replaceList(it1) }
            adapterComment.notifyDataSetChanged()
        }

        return binding.root
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