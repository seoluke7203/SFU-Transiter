package com.example.sfutransiter.views.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentRegisterBinding
import com.example.sfutransiter.views.MainFragment

class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var registerInterface: RegisterInterface

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var userName: String
    private lateinit var password: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegisterInterface)
            registerInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement RegisterInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO get API
//        val userRepo = AWSRepo(RetrofitAPI.getAWSInstance())
//        val userViewModelFactory = MyViewModelFactory(userRepo)
//        val userViewModel =
//            ViewModelProvider(this, userViewModelFactory)[UseViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupInputs()
        setupButtons()

        return binding.root
    }

    private fun setupButtons() {
        binding.btnSave.setOnClickListener {
            // TODO save data
            registerInterface.popBackToMain()
        }
        binding.btnCancel.setOnClickListener {
            registerInterface.popBackToMain()
        }
    }

    private fun setupInputs() {
        firstName = binding.etxtFirstName.text.toString()
        lastName = binding.etxtLastName.text.toString()
        email = binding.etxtEmail.text.toString()
        userName = binding.etxtUsername.text.toString()
        password = binding.etxtPassword.text.toString()
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