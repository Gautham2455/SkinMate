package com.example.skinmate.ui.home.appointments

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.ResponseInformationXXXXXX
import com.example.skinmate.ui.home.bookingAppointment.AppointmentSummary
import com.example.skinmate.utils.OnClickInterface
import com.example.skinmate.utils.OnClickInterface_
import com.example.skinmate.utils.RemainingTime
import com.example.skinmate.utils.StringToValue
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AppointmentAdapter(val apoointmentList:List<ResponseInformationXXXXXX>,
                         val onClickPosition: OnClickInterface,val onClickPosition_: OnClickInterface_
)
    : RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_card,parent,false)
        return AppointmentHolder(itemView)
    }


    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        val appointment=apoointmentList.get(position)
        if(appointment.familyFirstName.isNullOrEmpty())
            holder.patient_name.setText("Self")
        else
            holder.patient_name.setText(appointment.familyFirstName+" "+appointment.familyLastName)
        holder.doctor.setText(appointment.firstName+" "+appointment.designation)
        holder.appointment_date.setText(appointment.dateOfAppointment.date.subSequence(0,10))
        holder.appointment_time.setText(appointment.timeOfAppointment.time.firstOrNull())
        holder.patient_id.setText(appointment.appointmentId.toString())
        holder.dermatology_service.setText(appointment.serviceType)
//        holder.checkinBtn.setEnabled(btnEnable(appointment.timeOfAppointment.time.firstOrNull()))
        holder.checkinBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onClickPosition.getViewPosition(position)
            }
        })
        holder.options.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onClickPosition_.getViewPosition_(position)
            }
        })

    }




    override fun getItemCount(): Int {
        return  apoointmentList.size
    }


    class AppointmentHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val patient_name=itemView.findViewById<TextView>(R.id.tv_patient_name)
        val dermatology_service=itemView.findViewById<TextView>(R.id.tv_dermatology_service)
        val doctor=itemView.findViewById<TextView>(R.id.tv_doctor)
        val appointment_date=itemView.findViewById<TextView>(R.id.tv_appointment_date)
        val appointment_time=itemView.findViewById<TextView>(R.id.tv_appointment_time)
        val patient_id=itemView.findViewById<TextView>(R.id.tv_patient_id)
        val checkinBtn=itemView.findViewById<Button>(R.id.btn_checkin)
        val options=itemView.findViewById<ImageView>(R.id.img_appointment_menu)

    }

}