package com.example.skinmate.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.familyMemberList
import kotlinx.coroutines.Dispatchers

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val familyMemberData=UserRepository.getInstance(application)
    //[0].responseInformation.get(0).address

    fun getFamilyMembers(customerid:String):LiveData<familyMemberList> = liveData(Dispatchers.IO) {
        emitSource(familyMemberData.getFamilyMemberCall(customerid))
    }
}