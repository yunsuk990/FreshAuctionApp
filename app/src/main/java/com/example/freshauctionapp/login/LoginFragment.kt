package com.example.freshauctionapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.freshauctionapp.R
import com.example.freshauctionapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private val TAG = "LoginFragment"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val id = binding.fieldId.text.toString()
            val password = binding.fieldPassword.text.toString()

            if(id.isBlank() || password.isBlank()){
                Toast.makeText(requireContext(), "아이디/패스워드 입력해주세요", Toast.LENGTH_LONG).show()
            }else{
                binding.progressLoader.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener { task ->
                        task.addOnSuccessListener {
                            binding.fieldId.text = null
                            binding.fieldPassword.text = null
                            binding.progressLoader.visibility = View.GONE

                            findNavController().navigate(R.id.action_global_searchFragment)
                        }

                        task.addOnFailureListener{
                            binding.progressLoader.visibility = View.GONE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}