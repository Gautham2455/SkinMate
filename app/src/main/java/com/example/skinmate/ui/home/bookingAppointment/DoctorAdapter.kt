package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.ResponseInformationXXX

class DoctorAdapter(val doctorArray:List<ResponseInformationXXX>, context:Context):
    RecyclerView.Adapter<DoctorAdapter.DoctorCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.doctor_card,parent,false)
        return DoctorCardHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorCardHolder, position: Int) {
        val doctor=doctorArray[position]
        holder.doctorDetails.setText("${doctor.firstName} ${doctor.lastName} ${doctor.designation}")
    }

    override fun getItemCount(): Int {
        return doctorArray.size
    }

    class DoctorCardHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var doctorDetails=itemView.findViewById<TextView>(R.id.doctor_details)
    }
}