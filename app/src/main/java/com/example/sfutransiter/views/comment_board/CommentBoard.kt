package com.example.sfutransiter.views.comment_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentCommentBoardBinding

private const val ARG_ROUTE_ID = "routeId"

class CommentBoard : Fragment() {
    private var _binding: FragmentCommentBoardBinding? = null
    private val binding get() = _binding!!

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