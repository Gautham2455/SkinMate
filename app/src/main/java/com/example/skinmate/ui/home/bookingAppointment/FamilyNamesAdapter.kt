package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.FamilyMemberDetails
import com.example.skinmate.utils.OnClickInterface

class FamilyNamesAdapter(val contex:Context,val familyList:List<FamilyMemberDetails>,
val onclick: OnClickInterface
) : RecyclerView.Adapter<FamilyNamesAdapter.FamilyNamesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyNamesHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.familymember_names,parent,false)
        return FamilyNamesHolder(itemView)
    }

    override fun onBindViewHolder(holder: FamilyNamesHolder, position: Int) {
        val name=familyList.get(position)
        holder.familyNames.setText("${name.firstName} ${name.lastName}")

        holder.familyNames.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                onclick.getViewPosition(position)
            }
        })

    }

    override fun getItemCount(): Int {
        return familyList.size
    }


    class FamilyNamesHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val familyNames=itemView.findViewById<TextView>(R.id.familyMember_Name)

    }
}