package com.example.freshauctionapp.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.freshauctionapp.R
import com.example.freshauctionapp.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment() {
    private val TAG = "UserFragment"
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseAuth.getInstance().currentUser?.let{
            binding.textView2.text = it.email
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}