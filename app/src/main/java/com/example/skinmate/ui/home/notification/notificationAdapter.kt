package com.example.skinmate.ui.home.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.FamilyMemberDetails
import com.example.skinmate.ui.home.accountDetails.FamilyAdapter
import de.hdodenhof.circleimageview.CircleImageView

class notificationAdapter (val notificationArray:ArrayList<String>, context: Context):RecyclerView.Adapter<notificationAdapter.NotificationCardHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationCardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_card,parent,false)
        return notificationAdapter.NotificationCardHolder(itemView)
    }


    override fun onBindViewHolder(holder: NotificationCardHolder, position: Int) {
        val notifications = notificationArray[position]
        holder.notificationText.setText("${notifications}")
    }

    override fun getItemCount(): Int {
        return notificationArray.size
    }



    class NotificationCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var notificationText = itemView.findViewById<TextView>(R.id.tv_notification)

    }
}