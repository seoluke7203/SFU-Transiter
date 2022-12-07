package com.example.sfutransiter.views.register

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
import com.example.sfutransiter.databinding.FragmentRegisterBinding
import com.example.sfutransiter.model.ResponseError
import com.example.sfutransiter.model.User
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.UserViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.util.observeOnce
import com.example.sfutransiter.views.MainFragment
import com.example.sfutransiter.views.components.ProgressController

class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var registerInterface: RegisterInterface

    private lateinit var userViewModel: UserViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegisterInterface)
            registerInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement RegisterInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val userViewModelFactory = MyViewModelFactory(userRepo)
        userViewModel =
            ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupButtons()

        return binding.root
    }

    private fun setupButtons() {
        binding.btnSave.setOnClickListener {
            val progressController = ProgressController(binding.btnSave, binding.progress.root)
            val firstName = binding.etxtFirstName.text.toString()
            val lastName = binding.etxtLastName.text.toString()
            val email = binding.etxtEmail.text.toString()
            val userName = binding.etxtUsername.text.toString()
            val password = binding.etxtPassword.text.toString()

            progressController.start()
            userViewModel.createUser(
                User.RequestBody(
                    userName,
                    password,
                    email,
                    firstName,
                    lastName
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
                    progressController.end()
                    return@observeOnce
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.save_success),
                    Toast.LENGTH_SHORT
                ).show()
                registerInterface.popBackToMain()
            }
        }
        binding.btnCancel.setOnClickListener {
            registerInterface.popBackToMain()
        }
    }

    interface RegisterInterface {
        fun popBackToMain()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Register()

        val TAG: String = Register::class.java.simpleName
    }
}