package com.example.sfutransiter.views.comment_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentCommentBoardBinding

class CommentBoard : Fragment() {
    private var _binding: FragmentCommentBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CommentBoard()

        val TAG = CommentBoard::class.java.simpleName
    }
}