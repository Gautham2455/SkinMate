package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.doctorListResponse
import com.example.skinmate.data.responses.familyMemberList
import com.example.skinmate.data.responses.familyMemberListItem
import com.example.skinmate.data.responses.familyMemberResponse
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.bookingAppointment.DoctorAdapter
import com.example.skinmate.ui.home.bookingAppointment.ScheduleAppointmentFragment

class FamilyMemberListFragment : BaseFragment() {


    private val viewModel by viewModels<HomeViewModel>()
    var familyResponse : familyMemberResponse?=null
    lateinit var familyAdapter : FamilyAdapter

    companion object{
        fun newInstance() = FamilyMemberListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Family Members")

        val view:View = inflater?.inflate(R.layout.family_members_option, container, false)

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")


        viewModel.getFamilyMembersList("Bearer $token",custId!!).observe(requireActivity()){
            val familyAdapter = FamilyAdapter(it.get(0).responseInformation,requireContext())
            val dl = view.findViewById<RecyclerView>(R.id.family_member_list)
            if(it.get(0).responseInformation.size!=0){
                dl.layoutManager = LinearLayoutManager(requireContext())
                dl.setAdapter(familyAdapter)
                Log.v("familyList",it.get(0).responseInformation.get(0).firstName)
            }

        }



            val fabBtn =view.findViewById<View>(R.id.fab)
        fabBtn.setOnClickListener {
            replace(R.id.fragment_container,AddFamilyMemberFragment.newInstance())
        }

        return view
    }
}