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
    private val editCustomerData=UserRepository.getInstance(application)
    private val bookedAppointmentData=UserRepository.getInstance(application)
    //[0].responseInformation.get(0).address

    fun getFamilyMembersList(token:String,customerid:String):LiveData<familyMemberList> = liveData(Dispatchers.IO) {
        emitSource(familyMemberListData.getFamilyMemberListCall(token,customerid))
    }

    fun getFamilyMember(token:String,familyProfileId:String):LiveData<familyMemberResponse> = liveData(Dispatchers.IO) {
        emitSource(familyMemberData.getFamilyMemberCall(token,familyProfileId))
    }

    fun getSubService(token:String,serviceId:String):LiveData<subServiceResponse> = liveData(Dispatchers.IO) {
        emitSource(subServiceData.getSubServiceCall(token,serviceId))
    }

    fun postDeleteFamilyMember(token:String,familyProfileId:String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(deletedFamilyMember.deleteFamilyMemberCall(token,familyProfileId))
    }

    fun getDoctorList(token:String,serviceId:String):LiveData<doctorListResponse> = liveData (Dispatchers.IO){
        emitSource(doctorListData.doctorListCall(token,serviceId))
    }

    fun postAddFamilyMember(token:String,customerId:String, relationshipId:String,firstName:String, lastName:String,gender:String,dob:String,
                            bloodGroup:String,address:String,insuranceInformation:String,emeregencyContactName:String,
                            emeregencyNumber:String):LiveData<List<generalResponse>> = liveData {

                      emitSource(addFamilyMemberData.addFamilyMemberCall(token,customerId, relationshipId, firstName, lastName, gender,dob, bloodGroup, address, insuranceInformation, emeregencyContactName, emeregencyNumber))
    }

    fun getCustomerDetails(token:String,id:String):LiveData<customerViewResponse> = liveData(Dispatchers.IO) {
        emitSource(customerViewData.getCustomerViewCall(token,id))
    }

    fun postEditCustomer(token:String,customerId:String,address:String,email : String,
                         insuranceInformation:String,emeregencyContactName:String,
                         emeregencyNumber:String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
                             emitSource(editCustomerData.postEditCustomerDetailsCall(token,customerId, address, email, insuranceInformation, emeregencyContactName, emeregencyNumber))
    }

    fun getBookedAppointments(token:String,doctorId:String,date:String):LiveData<bookedAppointmentResponse> =
        liveData(Dispatchers.IO) {
            emitSource(bookedAppointmentData.getBookedAppointmentCall(token,doctorId, date))
        }

}