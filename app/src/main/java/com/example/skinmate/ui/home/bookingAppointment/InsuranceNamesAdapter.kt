package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.FamilyMemberDetails
import com.example.skinmate.data.responses.InsuranceList
import com.example.skinmate.data.responses.ResponseInformationXXXXX
import com.example.skinmate.utils.OnClickInterface
import com.example.skinmate.utils.OnClickInterface_

class InsuranceNamesAdapter(val contex:Context, val insuranceList: List<ResponseInformationXXXXX>,
                            val onclick: OnClickInterface_
) : RecyclerView.Adapter<InsuranceNamesAdapter.InsuranceNamesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsuranceNamesHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.insurance_names,parent,false)
        return InsuranceNamesHolder(itemView)
    }

    override fun onBindViewHolder(holder: InsuranceNamesHolder, position: Int) {
        val name=insuranceList.get(position)
        holder.insurance_name.setText(name.insuranceInformation)

        holder.insurance_name.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                onclick.getViewPosition_(position)
            }
        })

    }

    override fun getItemCount(): Int {
        return insuranceList.size
    }


    class InsuranceNamesHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val insurance_name=itemView.findViewById<TextView>(R.id.insurance_name)

    }
}