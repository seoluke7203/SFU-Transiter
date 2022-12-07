package com.example.sfutransiter.views.add_comment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.R
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentAddCommentBinding
import com.example.sfutransiter.model.BusStopReview
import com.example.sfutransiter.model.ResponseError
import com.example.sfutransiter.model.Safety
import com.example.sfutransiter.model.User
import com.example.sfutransiter.model.view_model.BusReviewViewModel
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.UserViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.util.observeOnce
import com.example.sfutransiter.views.MainFragment

private const val ARG_ROUTE_ID = "routeId"

class AddComment : Fragment() {
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var awsViewModel : BusReviewViewModel
    private lateinit var userViewModel : UserViewModel
    private lateinit var routeId: String
    private lateinit var addCommentInterface : AddCommentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddCommentInterface)
            addCommentInterface = context
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
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)

        val awsRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val awsViewModelFactory = MyViewModelFactory(awsRepo)
        awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]

        val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val userViewModelFactory = MyViewModelFactory(userRepo)
        userViewModel =
            ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

        binding.btnDiscard.setOnClickListener{
            addCommentInterface.popBackToMain()
        }

        binding.btnPost.setOnClickListener{
            insertReview()
            Thread.sleep(1000) //janky bandaid to wait for db update
            addCommentInterface.popBackToMain()
        }
        return binding.root
    }

    private fun insertReview() {
        val routeNo = routeId
        val comment = binding.txtComment.text.toString()
        var safety : String? = null
        var crowd = 0
        var authorRn : String? = null
        var userName = ""

        if(binding.radioCrowd.checkedRadioButtonId == binding.low.id)
            crowd = 1
        else if (binding.radioCrowd.checkedRadioButtonId == binding.med.id)
            crowd = 2
        else
            crowd = 3

        if(binding.radioSafety.checkedRadioButtonId == binding.safe.id)
            safety = Safety.GREEN.level
        else if (binding.radioSafety.checkedRadioButtonId == binding.moderate.id)
            safety = Safety.ORANGE.level
        else
            safety = Safety.RED.level

        if (User.getCurrentUser().userName == "") {
            userName = "Anonymous"
        }
        else {
            userName = User.getCurrentUser().userName
            authorRn = User.getCurrentUser().userRn
        }

        awsViewModel.insertBusStopReview(
            routeId,
            BusStopReview.RequestBody(
                routeNo,
                comment,
                safety,
                crowd,
                authorRn,
                userName
            )
        ).observeOnce(viewLifecycleOwner) {
            if (!it.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    getString(
                        R.string.fail_register,
                        ResponseError.fromJsonString(
                            it.errorBody()!!.string()
                        ).error.details[0].message
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                return@observeOnce
            }
            Toast.makeText(
                requireContext(),
                getString(R.string.save_success),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    interface AddCommentInterface {
        fun popBackToMain()
    }

    companion object {
        @JvmStatic
        fun newInstance(routeId: String) =
            AddComment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUTE_ID, routeId)
                }
            }

        val TAG: String = AddComment::class.java.simpleName
    }
}