package com.example.sampleslinmate.utils

import java.util.regex.Pattern

class InputValidation {

    fun isemailValid(email : String) : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(mob_no : String) : Boolean{
        return android.util.Patterns.PHONE.matcher(mob_no).matches()
    }

    fun passwordValid(password : String) : Boolean{
        if(password.length>=6)
            return true
        return false
    }
}