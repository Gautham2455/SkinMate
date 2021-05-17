package com.example.skinmate.ui.home.appointments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.ResponseInformationXXXXXX

class AppointmentAdapter(val apoointmentList:List<ResponseInformationXXXXXX>) : RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

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
        holder.appointment_date.setText(appointment.dateOfAppointment.date.subSequence(0,9))
        holder.appointment_time.setText(appointment.timeOfAppointment.time[0].toString())
        holder.patient_id.setText(appointment.appointmentId.toString())
        holder.dermatology_service.setText(appointment.serviceType)
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



    }
}