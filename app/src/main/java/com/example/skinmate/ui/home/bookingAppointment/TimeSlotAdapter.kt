package com.example.skinmate.ui.home.bookingAppointment

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R

class TimeSlotAdapter(val slots:MutableList<String>) :
    RecyclerView.Adapter<TimeSlotAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.time_slots_button,parent,false)
        return ButtonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val time=slots.get(position)
        holder.TimeSlots.setText(time)

    }

    override fun getItemCount(): Int {
        return slots.size
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var TimeSlots=itemView.findViewById<Button>(R.id.time_btn)


    }
}