package com.example.skinmate.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.*
import kotlinx.coroutines.Dispatchers

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val familyMemberListData=UserRepository.getInstance(application)
    private val familyMemberData=UserRepository.getInstance(application)
    private val subServiceData=UserRepository.getInstance(application)
    private val deletedFamilyMember=UserRepository.getInstance(application)
    private val doctorListData=UserRepository.getInstance(application)
    private val addFamilyMemberData=UserRepository.getInstance(application)
    private val customerViewData=UserRepository.getInstance(application)
    //[0].responseInformation.get(0).address

    fun getFamilyMembersList(customerid:String):LiveData<familyMemberList> = liveData(Dispatchers.IO) {
        emitSource(familyMemberListData.getFamilyMemberListCall(customerid))
    }

    fun getFamilyMember(familyProfileId:String):LiveData<familyMemberResponse> = liveData(Dispatchers.IO) {
        emitSource(familyMemberData.getFamilyMemberCall(familyProfileId))
    }

    fun getSubService(serviceId:String):LiveData<subServiceResponse> = liveData(Dispatchers.IO) {
        emitSource(subServiceData.getSubServiceCall(serviceId))
    }

    fun postDeleteFamilyMember(familyProfileId:String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(deletedFamilyMember.deleteFamilyMemberCall(familyProfileId))
    }

    fun getDoctorList(serviceId:String):LiveData<doctorListResponse> = liveData (Dispatchers.IO){
        emitSource(doctorListData.doctorListCall(serviceId))
    }

    fun postAddFamilyMember(customerId:String, relationshipId:String,firstName:String, lastName:String,gender:String,
                            bloodGroup:String,address:String,insuranceInformation:String,emeregencyContactName:String,
                            emeregencyNumber:String):LiveData<List<generalResponse>> = liveData {

                      emitSource(addFamilyMemberData.addFamilyMemberCall(customerId, relationshipId, firstName, lastName, gender, bloodGroup, address, insuranceInformation, emeregencyContactName, emeregencyNumber))
    }

    fun getCustomerDetails(id:String):LiveData<customerViewResponse> = liveData(Dispatchers.IO) {
        emitSource(customerViewData.getCustomerViewCall(id))
    }
}