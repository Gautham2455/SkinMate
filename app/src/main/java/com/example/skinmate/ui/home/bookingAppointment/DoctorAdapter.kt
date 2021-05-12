package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.ResponseInformationXXX
import com.example.skinmate.utils.OnClickInterface

class DoctorAdapter(
    val doctorArray: List<ResponseInformationXXX>,
    val context: Context,
    val onClickPosition: OnClickInterface
):
    RecyclerView.Adapter<DoctorAdapter.DoctorCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.doctor_card,
            parent,
            false
        )
        return DoctorCardHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorCardHolder, position: Int) {
        val doctor=doctorArray[position]
        holder.doctorDetails.setText("${doctor.firstName} ${doctor.lastName} ${doctor.designation}")
        holder.doctorCard.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onClickPosition.getViewPosition(position)
            }
        })
        holder.doctorCard.setOnClickListener({
            if(holder.done_mark.isVisible){
                holder.done_mark.visibility=View.GONE
            }
            else
                holder.done_mark.visibility=View.VISIBLE


        })

    }

    override fun getItemCount(): Int {
        return doctorArray.size
    }

    class DoctorCardHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var doctorDetails=itemView.findViewById<TextView>(R.id.doctor_details)
        val doctorCard=itemView.findViewById<CardView>(R.id.doctor_card)
        val done_mark=itemView.findViewById<ImageView>(R.id.ImageView_selected_gender_male)

    }
}