package com.example.freshauctionapp.search

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshauctionapp.R
import com.example.freshauctionapp.database.DatabaseModule
import com.example.freshauctionapp.databinding.FragmentSearchBinding
import com.example.freshauctionapp.model.Fruits
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    var selectedFruit: String? = null
    var selectedDate: String? = null

    private val database by lazy {
        DatabaseModule.getDatabase(requireContext())
    }

//    private val searchAdapter by lazy {
//        SearchAdapter(database.freshDao())
//    }

    private val alertDialog by lazy {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("농산물을 선택해주세요.")
        builder.setItems(Fruits.values().map { it.holder}.toTypedArray()){ _, index ->
            with(Fruits.values()[index]){
                selectedFruit = this.name
                binding.textType.text = this.holder
            }
            checkCondition()
        }
        builder.setNegativeButton("취소", null)
        builder.create()
    }

    private fun checkCondition() {
        if(selectedDate != null && selectedFruit != null){
            binding.btnSearch.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccent, null))
            binding.btnSearch.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, null))
        }else{
            binding.btnSearch.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.dark_grey, null))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DividerItemDecoration(
            requireContext(),
            LinearLayoutManager(requireContext()).orientation
        ).run{
            binding.listSearch.addItemDecoration(this)
        }

        selectedFruit = null
        selectedDate = null

        binding.layoutType.setOnClickListener{
            alertDialog.show()
        }

        binding.layoutDate.setOnClickListener{
            val currentCalendar = Calendar.getInstance().apply {
                time = Date(System.currentTimeMillis())
            }

            DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                    currentCalendar.apply {
                        set(Calendar.YEAR, i)
                        set(Calendar.MONTH, i2)
                        set(Calendar.DAY_OF_MONTH, i3)
                    }.run {
                        selectedDate = SimpleDateFormat("yyyy-MM-dd").format(currentCalendar.time)
                        //changeInputByte()
                    }
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}