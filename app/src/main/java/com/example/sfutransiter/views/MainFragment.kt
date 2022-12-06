package com.example.sfutransiter.views

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
import com.example.sfutransiter.databinding.FragmentMainBinding
import com.example.sfutransiter.model.ResponseError
import com.example.sfutransiter.model.User
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.UserViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.util.observeOnce
import com.example.sfutransiter.views.components.ProgressController

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainFragmentInterface: MainFragmentInterface

    private lateinit var progressController: ProgressController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentInterface)
            mainFragmentInterface = context
        else Log.e(TAG, "onAttach: MainActivity must implement MainFragmentInterface")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupButton()

        return binding.root
    }

    private fun setupButton() {
        binding.btnAnonymous.setOnClickListener {
            mainFragmentInterface.swapToSearchBy()
        }
        binding.btnRegister.setOnClickListener {
            mainFragmentInterface.swapToRegister()
        }
        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            val progressController = ProgressController(binding.btnLogin, binding.progress.root)
            val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
            val userViewModelFactory = MyViewModelFactory(userRepo)
            val userViewModel =
                ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
            val userName = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            progressController.start()
            userViewModel.checkUserAuthorized(userName, User.RequestBodyAuth(password))
                .observeOnce(viewLifecycleOwner) {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            getString(
                                R.string.fail_login,
                                ResponseError.fromJsonString(
                                    it.errorBody()!!.string()
                                ).error.details[0].message
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                        progressController.end()
                        return@observeOnce
                    }
                    if (!it.body()!!.authorized) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.Invalid_login),
                            Toast.LENGTH_SHORT
                        ).show()
                        progressController.end()
                        return@observeOnce
                    }
                    val body = it.body()!!
                    User.setCurrentUser(body.userRn, body.userName)
                    mainFragmentInterface.swapToSearchBy()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

        val TAG: String = MainFragment::class.java.simpleName
    }

    interface MainFragmentInterface {
        fun swapToSearchBy()
        fun swapToRegister()
    }
}