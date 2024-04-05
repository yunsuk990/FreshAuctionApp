package com.example.freshauctionapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.freshauctionapp.R
import com.example.freshauctionapp.databinding.FragmentRegisterBinding
import com.example.freshauctionapp.util.hideKeyboard
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private val TAG = "RegisterFragment"
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val id = binding.fieldRegisterId.text.toString()
            val pw = binding.fieldRegisterPassword.text.toString()
            val pw_confirm = binding.fieldRegisterPasswordConfirm.text.toString()

            when {
                id.isEmpty() -> Toast.makeText(requireContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show()
                pw.isEmpty() || pw_confirm.isEmpty() -> Toast.makeText(requireContext(), "패스워드를 입력하세요.", Toast.LENGTH_LONG).show()
                pw_confirm != pw -> Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                else -> {
                    binding.progressLoader.visibility = View.VISIBLE

                    firebaseAuth.createUserWithEmailAndPassword(id,pw)
                        .addOnCompleteListener{ task ->
                            task.addOnSuccessListener {
                                binding.fieldRegisterId.text = null
                                binding.fieldRegisterPassword.text = null
                                binding.fieldRegisterPasswordConfirm.text = null
                                binding.progressLoader.visibility = View.GONE

                                hideKeyboard()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}