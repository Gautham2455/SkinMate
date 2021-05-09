package com.example.sampleslinmate.utils

import android.util.Patterns
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.skinmate.ui.auth.SignUpFragment


class InputValidation {


    fun isemailValid(email : String) : Boolean{
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val valid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (valid)
            return true
        return false
    }

    fun isPhoneValid(mob_no : String) : Boolean{
        //return android.util.Patterns.PHONE.matcher(mob_no).matches()
        if (mob_no.length==10)
            return true
        return false

    }

    fun passwordValid(password : String) : Boolean{
        if(password.length>=4)
            return true
        return false
    }

    fun isPasswordEqual(password: String,confirmPassword : String) : Boolean{
        if(password==confirmPassword)
            return true
        return false
    }
}