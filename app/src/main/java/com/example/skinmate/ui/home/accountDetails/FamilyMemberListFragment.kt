package com.example.skinmate.ui.home.accountDetails

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
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
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.bookingAppointment.DoctorAdapter
import com.example.skinmate.ui.home.bookingAppointment.ScheduleAppointmentFragment
import com.example.skinmate.utils.OnClickInterface
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class FamilyMemberListFragment : BaseFragment(),OnClickInterface {


    private val viewModel by viewModels<HomeViewModel>()
    var familyResponse : List<familyMemberListItem>?=null
    lateinit var familyAdapter : FamilyAdapter
    var token :String?=null

    companion object{
        fun newInstance() = FamilyMemberListFragment()
        var familyProfileId : Int?=null
        var firstName : String?=null
        var lastName : String?=null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Family Members")

        val view:View = inflater?.inflate(R.layout.family_members_option, container, false)

        HomeActivity.bottomNavigationView.visibility = View.GONE



            val fabBtn =view.findViewById<View>(R.id.fab)
        fabBtn.setOnClickListener {
            replace(R.id.fragment_container,AddFamilyMemberFragment.newInstance())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        token=sharedPref!!.getString(SignInFragment.TOKEN,"none")

        viewModel.getFamilyMembersList("Bearer $token",custId!!).observe(this){
            familyResponse = it
            val familyAdapter =
                context?.let { it1 -> FamilyAdapter(it.get(0).responseInformation, it1,this) }
            val dl = view.findViewById<RecyclerView>(R.id.family_member_list)
            if(it.get(0).responseInformation.size!=0){
                dl.layoutManager = LinearLayoutManager(context)
                dl.setAdapter(familyAdapter)
                Log.v("familyList",it.get(0).responseInformation.get(0).firstName)
            }

        }

    }

    override fun getViewPosition(position: Int) {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottomdialogfragment_delete_edit_family_member)
        bottomSheetDialog?.show()
        val editMemberDetails = bottomSheetDialog?.findViewById<TextView>(R.id.tv_edit_family_member)
        familyProfileId = familyResponse!!.get(0).responseInformation.get(position).familyProfileId
        firstName =  familyResponse!!.get(0).responseInformation.get(position).firstName
        lastName =  familyResponse!!.get(0).responseInformation.get(position).lastName
        Log.v("ID", familyProfileId.toString())
        editMemberDetails!!.setOnClickListener {
            bottomSheetDialog?.dismiss()
            replace(R.id.fragment_container,EditFamilyMemberDetailsFragment.newInstance()) }
        val deleteMember = bottomSheetDialog?.findViewById<TextView>(R.id.tv_delete_member)
        deleteMember!!.setOnClickListener {
            bottomSheetDialog?.dismiss()
            openDialog(firstName!!, lastName!!, familyProfileId!!) }

    }

    private fun openDialog(firstName:String,lastName:String,familyProfileId:Int) {
        val dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.layout_delete_member)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
        val familyMemberName = dialog?.findViewById<TextView>(R.id.tv_member_name)
        familyMemberName?.setText(firstName + " " + lastName + "?")
        val id=familyProfileId.toString()
        val yesBtn = dialog?.findViewById(R.id.btn_yes) as Button
        val noBtn = dialog?.findViewById<Button>(R.id.btn_No)
        noBtn.setOnClickListener { dialog?.dismiss() }
        yesBtn.setOnClickListener {
            dialog?.dismiss()
            deleteMember(id) }
    }

    private fun deleteMember(id: String) {

        Log.v("delteId",id)
        viewModel.deleteMember("Bearer $token",id).observe(requireActivity()){
            if(it.get(0).responseMessage!!) {
                Toast.makeText(requireContext(),it.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()
                replace(R.id.fragment_container, FamilyMemberListFragment.newInstance())
                fragmentManager?.popBackStack()
            }

        }

    }
}