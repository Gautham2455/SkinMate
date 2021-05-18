package com.example.skinmate.ui.home.checkIn

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SymptomCeckBinding

class SymptomCheckFragment : BaseFragment() {

    private lateinit var binding : SymptomCeckBinding


    companion object{
        fun newInstance() = SymptomCheckFragment()
        var selected:String?=null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Check In")
        binding = DataBindingUtil.inflate(inflater,
            R.layout.symptom_ceck,container,false)

        binding.coughCard.setOnClickListener {
            if(!binding.ImageViewSelectedCough.isVisible) {
                binding.ImageViewSelectedCold.isVisible=false
                binding.ImageViewSelectedFever.isVisible=false
                binding.ImageViewSelectedNone.isVisible=false
                binding.ImageViewSelectedCough.isVisible=true

                binding.coughCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

               selected = binding.tvCough.toString()

            }
            else {
                binding.ImageViewSelectedCough.isVisible=false
                binding.coughCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
        }

        binding.feverCard.setOnClickListener {
            if(!binding.ImageViewSelectedFever.isVisible) {
                binding.ImageViewSelectedCold.isVisible=false
                binding.ImageViewSelectedCough.isVisible=false
                binding.ImageViewSelectedNone.isVisible=false
                binding.ImageViewSelectedFever.isVisible=true

                binding.feverCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                selected = binding.tvMedical.toString()

            }
            else {
                binding.ImageViewSelectedFever.isVisible=false
                binding.feverCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
        }

        binding.coldCard.setOnClickListener {
            if(!binding.ImageViewSelectedCold.isVisible) {
                binding.ImageViewSelectedFever.isVisible=false
                binding.ImageViewSelectedCough.isVisible=false
                binding.ImageViewSelectedNone.isVisible=false
                binding.ImageViewSelectedCold.isVisible=true

                binding.coldCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                selected = binding.tvCold.toString()

            }
            else {
                binding.ImageViewSelectedCold.isVisible=false
                binding.coldCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
        }

        binding.noSymptomCard.setOnClickListener {
            if(!binding.ImageViewSelectedNone.isVisible) {
                binding.ImageViewSelectedFever.isVisible=false
                binding.ImageViewSelectedCough.isVisible=false
                binding.ImageViewSelectedCold.isVisible=false
                binding.ImageViewSelectedNone.isVisible=true

                binding.noSymptomCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                selected = "none"

            }
            else {
                binding.ImageViewSelectedNone.isVisible=false
                binding.noSymptomCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
        }

        binding.confirmBtn.setOnClickListener {
                replace(R.id.fragment_container,CheckDoneFragment.newInstance())}


        return binding.root
    }
}