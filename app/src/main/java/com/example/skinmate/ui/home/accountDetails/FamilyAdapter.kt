package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.R
import com.example.skinmate.data.responses.FamilyMemberDetails
import com.example.skinmate.utils.OnClickInterface
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class FamilyAdapter(val familyArray:List<FamilyMemberDetails>, context: Context,val onClickPosition: OnClickInterface) :
    RecyclerView.Adapter<FamilyAdapter.FamilyCardHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyCardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.family_member_card,parent,false)
        return FamilyCardHolder(itemView)
    }



    override fun onBindViewHolder(holder: FamilyCardHolder, position: Int) {
        val familyMember = familyArray[position]
        holder.familyMemberName.setText("${familyMember.firstName} ${familyMember.lastName}")
        holder.familyMemberMenu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onClickPosition.getViewPosition(position)
            }
        })
    }

    override fun getItemCount(): Int {
        return familyArray.size
    }

    class FamilyCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var familyMemberName = itemView.findViewById<TextView>(R.id.family_member_name)
        var familyMemberMenu = itemView.findViewById<ImageView>(R.id.img_family_member_menu)
        var familyMemberImg = itemView.findViewById<CircleImageView>(R.id.family_member_image)
    }
}