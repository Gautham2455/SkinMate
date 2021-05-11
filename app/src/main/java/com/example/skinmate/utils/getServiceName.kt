package com.example.skinmate.utils

class getServiceName()
{
    fun checkid(id:String): String? {
        var  subSErvice:String?=null
        when(id){
            "1" -> subSErvice="Telehealth Video"
            "2" -> subSErvice="Medical"
            "3" -> subSErvice="Cosmetic Appointment"
            "4" -> subSErvice="Skin Care"
        }

        return subSErvice
    }
}