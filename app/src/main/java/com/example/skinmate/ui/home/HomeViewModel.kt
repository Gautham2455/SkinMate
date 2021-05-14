package com.example.skinmate.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.*
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody



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
    private val addInsuranceData=UserRepository.getInstance(application)
    private val addAppointmentData=UserRepository.getInstance(application)
    private val registerIData=UserRepository.getInstance(application)
    private val emailOtpData=UserRepository.getInstance(application)
    private val mainServiceData=UserRepository.getInstance(application)

    private val appointListData=UserRepository.getInstance(application)
    private val appointmentSatusData=UserRepository.getInstance(application)

    private val editFamilyMemberDetailsData = UserRepository.getInstance(application)
    private val deleteMemberData = UserRepository.getInstance(application)
    private val memberViewData = UserRepository.getInstance(application)


    fun getFamilyMembersList(token:String,customerid:String):LiveData<List<familyMemberListItem>> = liveData(Dispatchers.IO) {
        emitSource(familyMemberListData.getFamilyMemberListCall(token,customerid))
    }

    fun getFamilyMember(token:String,familyProfileId:String):LiveData<familyMemberResponse> = liveData(Dispatchers.IO) {
        emitSource(familyMemberData.getFamilyMemberCall(token,familyProfileId))
    }

    fun getSubService(token:String,serviceId:String):LiveData<subServiceResponse> = liveData(Dispatchers.IO) {
        emitSource(subServiceData.getSubServiceCall(token,serviceId))
    }

    fun postDeleteFamilyMember(token:String,familyProfileId: String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(deletedFamilyMember.deleteFamilyMemberCall(token,familyProfileId))
    }

    fun getDoctorList(token:String,serviceId:String):LiveData<doctorListResponse> = liveData (Dispatchers.IO){
        emitSource(doctorListData.doctorListCall(token,serviceId))
    }

    fun postAddFamilyMember(token:String,customerId:String, relationshipId:String,firstName:String, lastName:String,gender:String,dob:String,
                            bloodGroup:String,address:String,insuranceInformation:String,emeregencyContactName:String,
                            emeregencyNumber:String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {

                      emitSource(addFamilyMemberData.addFamilyMemberCall(token,customerId, relationshipId, firstName, lastName, gender,dob, bloodGroup, address, insuranceInformation, emeregencyContactName, emeregencyNumber))
    }

    fun getCustomerDetails(token:String,customerId:String):LiveData<customerViewResponse> = liveData(Dispatchers.IO) {
        emitSource(customerViewData.getCustomerViewCall(token,customerId))
    }

    fun postEditCustomer(token:String,customerId:String,address:String,email:String,
                         insuranceInformation:String,emeregencyContactName:String,
                         emeregencyNumber:String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
                             emitSource(editCustomerData.postEditCustomerDetailsCall(token,customerId, address,email, insuranceInformation, emeregencyContactName, emeregencyNumber))
    }

    fun getBookedAppointments(token:String,doctorId:String,date:String):LiveData<bookedAppointmentResponse> =
        liveData(Dispatchers.IO) {
            emitSource(bookedAppointmentData.getBookedAppointmentCall(token,doctorId, date))
        }



    fun postAddInsurance(token:String,requestBody: RequestBody):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(addInsuranceData.postAddInsuranceCall(token,requestBody))

    }

    fun postAddAppointment(token:String,requestBody: RequestBody):LiveData<List<generalResponse>> = liveData (Dispatchers.IO){
        emitSource(addAppointmentData.postAddAppointmentCall(token,requestBody))
    }

    fun postRegisterID(token: String,customerId: String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(registerIData.postRegisterIDCall(token, customerId))
    }

    fun postEmailVerify(token: String,customerId: String,otp:String):LiveData<List<generalResponse>> =
        liveData(Dispatchers.IO) {
            emitSource(emailOtpData.postVerifyEmailOtpCall(token, customerId, otp))
        }

    fun getMAinServices(token: String):LiveData<MainServiceResponse> = liveData(Dispatchers.IO) {
        emitSource(mainServiceData.getMainService(token))
    }


    fun getAppointmentList(token: String,customerId: String):LiveData<AppointmentList> = liveData(Dispatchers.IO) {
        appointListData.appointmentListCall(token, customerId)
    }

    fun getAppointmentStatus(token: String,appointmentId:String,status:String): LiveData<List<generalResponse>> =
        liveData {
            emitSource(appointmentSatusData.AppointStatusCAll(token, appointmentId, status))
        }


    fun putFamilyMemberEdidtDetails(token: String,familyProfileId: String,requestBody: RequestBody):LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(editFamilyMemberDetailsData.editFamilyMemberDetailsCall(token, familyProfileId,requestBody))
    }

    fun deleteMember(token: String,familyProfileId: String):LiveData<List<generalResponse>> = liveData(Dispatchers.IO){
        emitSource(deleteMemberData.deleteFamilyMemberCall(token,familyProfileId))
    }

    fun getMemberView(token:String,familyProfileId:String):LiveData<memberViewResponse> = liveData(Dispatchers.IO) {
        emitSource(memberViewData.getMemberViewCall(token,familyProfileId))
    }

}