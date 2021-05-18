package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.utils.OnClickInterface_

class AfternoonTimeSlotAdapter(val slots:MutableList<String>, val context:Context, val onClickPosition: OnClickInterface_) :
    RecyclerView.Adapter<AfternoonTimeSlotAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.time_slots_button,parent,false)
        return ButtonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val time=slots.get(position)
        holder.TimeSlots.setText(time)
        holder.TimeSlots.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onClickPosition.getViewPosition_(position)
                if(holder.flag==false) {
                    holder.TimeSlots.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_background_light))
                    holder.flag=true
                }
                else{
                    holder.TimeSlots.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    holder.flag=false
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return slots.size
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var TimeSlots=itemView.findViewById<Button>(R.id.time_btn)
        var flag:Boolean=false

    }
}