package com.example.skinmate.utils

import android.util.Patterns
import com.example.skinmate.R
import kotlinx.android.synthetic.main.fragment_add_family_member_setup_profile.*

class StringToValue {


    fun relationTypeTorealtionId(type : String) : String? {
        var id:String?=null
        when(type){
            "father" -> id="1"
            "mother" -> id="2"
            "brother"-> id="3"
            "sister" -> id="4"
            "chlidren" -> id="5"
            "wife" -> id="6"
            "grand father" -> id="7"
            "grand mother" -> id="8"
            "husband" -> id="9"
        }
        return id
    }

    fun genderToGenderId(type : String) : String? {
        var genderId:String?=null
        when(type){
            "Male" -> genderId="1"
            "Female" -> genderId="2"
            "Other"-> genderId="3"
        }
        return genderId
    }


}