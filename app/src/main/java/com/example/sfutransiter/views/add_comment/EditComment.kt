package com.example.sfutransiter.views.add_comment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.R
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentEditCommentBinding
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
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"
private const val ARG_STOP_RN = "stopRn"

class EditComment : Fragment() {
    private var _binding: FragmentEditCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var awsViewModel : BusReviewViewModel
    private lateinit var userViewModel : UserViewModel
    private lateinit var stopRn: String
    private lateinit var routeId: String
    private lateinit var editCommentInterface : EditCommentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditCommentInterface)
            editCommentInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement RegisterInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getString(ARG_ROUTE_ID)!!
            stopRn = it.getString(ARG_STOP_RN)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCommentBinding.inflate(inflater, container, false)

        val awsRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val awsViewModelFactory = MyViewModelFactory(awsRepo)
        awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]

        val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val userViewModelFactory = MyViewModelFactory(userRepo)
        userViewModel =
            ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

        val oldReview : LiveData<Response<BusStopReview.ResponseList>> = awsViewModel.listBusStopReviews(routeId)
        lateinit var oldReviewBody : BusStopReview.ResponseList
        println("debug:$routeId $stopRn")
        oldReview.observe(viewLifecycleOwner) {
            oldReviewBody = it.body()!!
            val oldReviewArray = oldReviewBody.list
            for(i in oldReviewArray)
                if(i.stopReviewRn == stopRn) {
                    binding.txtComment.setText(i.comment)
                    if (i.crowd == 1)
                        binding.radioCrowd.check(binding.low.id)
                    else if (i.crowd == 2)
                        binding.radioCrowd.check(binding.med.id)
                    else
                        binding.radioCrowd.check(binding.high.id)

                    if (i.safety == Safety.GREEN.level)
                        binding.radioSafety.check(binding.safe.id)
                    else if (i.safety == Safety.ORANGE.level)
                        binding.radioSafety.check(binding.moderate.id)
                    else
                        binding.radioSafety.check(binding.danger.id)
                    break
                }

            binding.btnCancel.setOnClickListener{
                editCommentInterface.popBackToMain()
            }

            binding.btnDelete.setOnClickListener{
                awsViewModel.deleteBusStopReview(routeId,stopRn)
                Thread.sleep(800) //janky bandaid to wait for db update
                editCommentInterface.popBackToMain()
            }

            binding.btnEdit.setOnClickListener{
                updateReview()
                Thread.sleep(800) //janky bandaid to wait for db update
                editCommentInterface.popBackToMain()
            }
        }

        binding.btnCancel.setOnClickListener{
            editCommentInterface.popBackToMain()
        }

        binding.btnDelete.setOnClickListener{
            awsViewModel.deleteBusStopReview(routeId,stopRn)
            Thread.sleep(800) //janky bandaid to wait for db update
            editCommentInterface.popBackToMain()
        }

        binding.btnEdit.setOnClickListener{
            updateReview()
            Thread.sleep(800) //janky bandaid to wait for db update
            editCommentInterface.popBackToMain()
        }

        return binding.root
    }

    private fun updateReview() {
        val routeNo: String? = null
        val comment = binding.txtComment.text.toString()
        var safety : String? = null
        var crowd : Int
        val authorRn = User.getCurrentUser().userRn
        val userName = User.getCurrentUser().userName

        if(binding.radioCrowd.checkedRadioButtonId == binding.low.id)
            crowd = 1
        else if (binding.radioCrowd.checkedRadioButtonId == binding.med.id)
            crowd = 2
        else
            crowd = 3

        if(binding.radioSafety.checkedRadioButtonId == binding.safe.id)
            safety = Safety.GREEN.level
        else if (binding.radioCrowd.checkedRadioButtonId == binding.moderate.id)
            safety = Safety.ORANGE.level
        else
            safety = Safety.RED.level

        awsViewModel.updateBusStopReview(
            routeId,
            stopRn,
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

    interface EditCommentInterface {
        fun popBackToMain()
    }

    companion object {
        @JvmStatic
        fun newInstance(routeId: String, stopRn : String) =
            EditComment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUTE_ID, routeId)
                    putString(ARG_STOP_RN, stopRn)
                }
            }

        val TAG: String = AddComment::class.java.simpleName
    }
}