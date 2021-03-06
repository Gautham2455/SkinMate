package com.example.skinmate.data.repositories

import android.app.Application
import android.util.Log

import androidx.lifecycle.MutableLiveData
import com.example.skinmate.data.network.RetrofitClient
import com.example.skinmate.data.network.SecuredRetrofitClient
import com.example.skinmate.data.responses.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(application: Application){

    val serviceSetterGetter = MutableLiveData<List<generalResponse>>()
    val registerEmail = MutableLiveData<List<generalResponse>>()
    val emailOtp = MutableLiveData<List<generalResponse>>()
    val checkDplicateUser = MutableLiveData<List<duplicateUserResponse>>()
    val registerUser = MutableLiveData<List<registerUserResponse>>()
    val loginUser = MutableLiveData<loginResponse>()
    val changePassword = MutableLiveData<List<passwordChangeResponse>>()
    val updatePassword= MutableLiveData<List<updatePasswordResponse>>()
    val familyMemberList=MutableLiveData<List<familyMemberListItem>>()
    val familyMember=MutableLiveData<familyMemberResponse>()
    val subService=MutableLiveData<subServiceResponse>()
    val deleteFamilyMember=MutableLiveData<List<generalResponse>>()
    val doctorList=MutableLiveData<doctorListResponse>()
    val addFamilyMember=MutableLiveData<List<generalResponse>>()
    val customerView=MutableLiveData<customerViewResponse>()
    val editCustomerDetails=MutableLiveData<List<generalResponse>>()
    val bookedAppointment=MutableLiveData<bookedAppointmentResponse>()
    val addInsurance = MutableLiveData<List<generalResponse>>()
    val addAppointment=MutableLiveData<List<generalResponse>>()
    val regiusterID=MutableLiveData<List<generalResponse>>()
    val verifyEmailOtp=MutableLiveData<List<generalResponse>>()
    val mainService=MutableLiveData<MainServiceResponse>()
    val appointmentList=MutableLiveData<AppointmentList>()
    val appointmentStatus=MutableLiveData<List<generalResponse>>()
    val editFamilyMeberDetails = MutableLiveData<List<generalResponse>>()
    val deleteMember = MutableLiveData<List<generalResponse>>()
    val familyMemberView = MutableLiveData<memberViewResponse>()
    val rescheduleAppointment=MutableLiveData<List<generalResponse>>()
    val notification=MutableLiveData<List<notificationResponse>>()
    val insuraceList=MutableLiveData<InsuranceList>()



    fun getServicesApiCall(otp : Int,mobileNumber :String): MutableLiveData<List<generalResponse>> {

        val call = RetrofitClient.apiInterface.verifyMobleOtp(otp,mobileNumber)

        call.enqueue(object: Callback<List<generalResponse>> {
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

//                val msg = data!!.get(0).responseInformation

                serviceSetterGetter.postValue(response.body())
            }
        })

        return serviceSetterGetter
    }

    fun checkDuplicateUserCall(email : String,phoneNumber : String) : MutableLiveData<List<duplicateUserResponse>> {

        val call = RetrofitClient.apiInterface.checkDuplicateUser(email,phoneNumber)

        call.enqueue(object : Callback<List<duplicateUserResponse>>{
            override fun onFailure(call: Call<List<duplicateUserResponse>>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<duplicateUserResponse>>,
                response: Response<List<duplicateUserResponse>>
            ) {

                checkDplicateUser.postValue(response.body() )
            }
        })
        return checkDplicateUser
    }

    fun loginUserCall(requesrBody: RequestBody) : MutableLiveData<loginResponse>{
        val call = RetrofitClient.apiInterface.userLogin(requesrBody)

        call.enqueue(object : Callback<loginResponse>{
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())

            }

            override fun onResponse(
                call: Call<loginResponse>,
                response: Response<loginResponse>
            ) {

                loginUser.postValue(response.body())
            }
        })
        return loginUser
    }

    fun changePasswordCall(token:String,customerId : String,oldPassword : String,newPassword :String) : MutableLiveData<List<passwordChangeResponse>>{
        val call= SecuredRetrofitClient.apiInterface.changePassword(token, customerId,oldPassword,newPassword)

        call.enqueue(object : Callback<List<passwordChangeResponse>>{
            override fun onFailure(call: Call<List<passwordChangeResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<passwordChangeResponse>>,
                response: Response<List<passwordChangeResponse>>
            ) {

                    changePassword.postValue(response.body())
            }
        })
        return changePassword
    }

    fun registerUserCall(requesrBody: RequestBody)
    : MutableLiveData<List<registerUserResponse>>{

        val call =RetrofitClient.apiInterface.userRegistration(requesrBody)

        call.enqueue(object :Callback<List<registerUserResponse>>{
            override fun onFailure(call: Call<List<registerUserResponse>>, t: Throwable) {

                Log.v("DEBUG : ", t.message.toString())

            }

            override fun onResponse(
                call: Call<List<registerUserResponse>>,
                response: Response<List<registerUserResponse>>
            ) {
                registerUser.postValue(response.body())
            }
        })

        return registerUser
    }

    fun registerEmailCall(email : String) :MutableLiveData<List<generalResponse>>{
        val call = RetrofitClient.apiInterface.registerEmail(email)

        call.enqueue(object : Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                registerEmail.postValue(response.body())
            }
        })
        return registerEmail
    }

    fun updatePasswordCall(email: String,password: String): MutableLiveData<List<updatePasswordResponse>> {

        val call = RetrofitClient.apiInterface.updatePassword(email,password)
        call.enqueue(object: Callback<List<updatePasswordResponse>> {
            override fun onFailure(call: Call<List<updatePasswordResponse>>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<updatePasswordResponse>>,
                response: Response<List<updatePasswordResponse>>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.get(0).responseInformation

                updatePassword.postValue(response.body())
            }
        })

        return updatePassword
    }

    fun verifyEmailOtpCall(email : String, otp :Int) :MutableLiveData<List<generalResponse>>{
        val call = RetrofitClient.apiInterface.verifyEmailOtp(email,otp)

        call.enqueue(object : Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                emailOtp.postValue(response.body())
            }
        })
        return emailOtp
    }

//    fun getFamilyMemberListCall(token:String,customerId:String) : MutableLiveData<familyMemberList>{
//        val call = SecuredRetrofitClient.apiInterface.familyList(token,customerId)
//
//        call.enqueue(object :Callback<familyMemberList>{
//            override fun onFailure(call: Call<familyMemberList>, t: Throwable) {
//
//            }
//
//            override fun onResponse(
//                call: Call<familyMemberList>,
//                response: Response<familyMemberList>
//            ) {
//                familyMemberList.postValue(response.body())
//                Log.v("user Repo",response.body().toString())
//            }
//        })
//        return familyMemberList
//    }

    fun getFamilyMemberListCall(token:String,customerId: String):MutableLiveData<List<familyMemberListItem>>{
        val call = SecuredRetrofitClient.apiInterface.familyList(token,customerId)

        call.enqueue(object :Callback<List<familyMemberListItem>>{
            override fun onFailure(call: Call<List<familyMemberListItem>>, t: Throwable) {
                    Log.v("T->",t.toString())
            }

            override fun onResponse(
                call: Call<List<familyMemberListItem>>,
                response: Response<List<familyMemberListItem>>
            ) {
                familyMemberList.postValue(response.body())
            }

        })
        return familyMemberList

    }


    fun getFamilyMemberCall(token:String,id :String) : MutableLiveData<familyMemberResponse>
    {
        val call=SecuredRetrofitClient.apiInterface.famllyMember(token,id)

        call.enqueue(object :Callback<familyMemberResponse>{
            override fun onFailure(call: Call<familyMemberResponse>, t: Throwable) {
                Log.v("Family member","Error")

            }

            override fun onResponse(
                call: Call<familyMemberResponse>,
                response: Response<familyMemberResponse>
            ) {
                familyMember.postValue(response.body())
            }
        })
        return familyMember
    }

    fun getSubServiceCall(token:String,serviceId:String):MutableLiveData<subServiceResponse>{
        val call=SecuredRetrofitClient.apiInterface.getSubService(token,serviceId)

        call.enqueue(object : Callback<subServiceResponse>{
            override fun onFailure(call: Call<subServiceResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<subServiceResponse>,
                response: Response<subServiceResponse>
            ) {
                subService.postValue(response.body())
            }
        })
        return  subService
    }

    fun deleteFamilyMemberCall(token:String,familyProfileId: String):MutableLiveData<List<generalResponse>>{

        val call = SecuredRetrofitClient.apiInterface.deleteMember(token,familyProfileId)

        call.enqueue(object : Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                deleteFamilyMember.postValue(response.body())
            }
        })
        return  deleteFamilyMember
    }

    fun doctorListCall(token:String,serviceId:String):MutableLiveData<doctorListResponse>{
        val call=SecuredRetrofitClient.apiInterface.doctorList(token,serviceId)

        call.enqueue(object :Callback<doctorListResponse>{
            override fun onFailure(call: Call<doctorListResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<doctorListResponse>,
                response: Response<doctorListResponse>
            ) {
                doctorList.postValue(response.body())
            }
        })
        return doctorList
    }

    fun addFamilyMemberCall(token:String,customerId:String, relationshipId:String,firstName:String, lastName:String,gender:String,dob:String,
                            bloodGroup:String,address:String,insuranceInformation:String,emeregencyContactName:String,
                            emeregencyNumber:String):MutableLiveData<List<generalResponse>>{

        val call =SecuredRetrofitClient.apiInterface.addFamilyMember(token,customerId,relationshipId,firstName,lastName,
        gender,dob,bloodGroup,address, insuranceInformation, emeregencyContactName, emeregencyNumber)

        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                addFamilyMember.postValue(response.body())
            }
        })
        return addFamilyMember
    }

    fun getCustomerViewCall(token:String,customerId:String):MutableLiveData<customerViewResponse>{
        val call =SecuredRetrofitClient.apiInterface.customerDetails(token,customerId)

        call.enqueue(object :Callback<customerViewResponse>{
            override fun onFailure(call: Call<customerViewResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<customerViewResponse>,
                response: Response<customerViewResponse>
            ) {
                customerView.postValue(response.body())
            }
        })
        return  customerView
    }

    fun postEditCustomerDetailsCall(token:String,customerId:String,address:String,email:String,
                                insuranceInformation:String,emeregencyContactName:String,
                                emeregencyNumber:String):MutableLiveData<List<generalResponse>>{

        val call=SecuredRetrofitClient.apiInterface.customerEdit(token,customerId, address,email, insuranceInformation, emeregencyContactName, emeregencyNumber)

        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                editCustomerDetails.postValue(response.body())
            }
        })
        return editCustomerDetails
    }

    fun getBookedAppointmentCall(token:String,doctorId:String,date:String):MutableLiveData<bookedAppointmentResponse>{
        val call =SecuredRetrofitClient.apiInterface.bookedAppointments(token,doctorId, date)

        call.enqueue(object :Callback<bookedAppointmentResponse>{
            override fun onFailure(call: Call<bookedAppointmentResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<bookedAppointmentResponse>,
                response: Response<bookedAppointmentResponse>
            ) {
                bookedAppointment.postValue(response.body())
            }
        })
        return bookedAppointment
    }


    fun postAddInsuranceCall(token: String,requestBody: RequestBody): MutableLiveData<List<generalResponse>> {
        val call = SecuredRetrofitClient.apiInterface.addInsurance(token,requestBody)
        call.enqueue(object : Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                addInsurance.postValue(response.body())
            }

        })
        return addInsurance

    }

    fun postAddAppointmentCall(token:String,requesrBody: RequestBody):MutableLiveData<List<generalResponse>>{
        val call=SecuredRetrofitClient.apiInterface.addAppointment(token,requesrBody)

        call.enqueue(object  :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                addAppointment.postValue(response.body())
            }
        })
        return  addAppointment
    }

    fun postRegisterIDCall(token: String,customerId: String):MutableLiveData<List<generalResponse>>{
        val call =SecuredRetrofitClient.apiInterface.registerId(token,customerId)

        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                regiusterID.postValue(response.body())
            }
        })
        return  regiusterID
    }

    fun postVerifyEmailOtpCall(token: String,customerId: String,otp:String):MutableLiveData<List<generalResponse>>{
        val call = SecuredRetrofitClient.apiInterface.emailOtpVerify(token, customerId, otp)

        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                verifyEmailOtp.postValue(response.body())
            }
        })
        return verifyEmailOtp
    }

    fun getMainService(token: String):MutableLiveData<MainServiceResponse>{
        val call =SecuredRetrofitClient.apiInterface.getMainSerice(token)

        call.enqueue(object :Callback<MainServiceResponse>{
            override fun onFailure(call: Call<MainServiceResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MainServiceResponse>,
                response: Response<MainServiceResponse>
            ) {
                mainService.postValue(response.body())
            }
        })
        return mainService
    }

    fun appointmentListCall(token: String,customerId: String):MutableLiveData<AppointmentList>{
        val call=SecuredRetrofitClient.apiInterface.ListOfAppointment(token, customerId)

        call.enqueue(object :Callback<AppointmentList>{
            override fun onFailure(call: Call<AppointmentList>, t: Throwable) {
                Log.v("List A","ERror")

            }

            override fun onResponse(
                call: Call<AppointmentList>,
                response: Response<AppointmentList>
            ) {
                appointmentList.postValue(response.body())
                Log.v("List A",response.body().toString())
            }
        })
        return  appointmentList
    }

    fun AppointStatusCAll(token: String,requestBody : RequestBody):MutableLiveData<List<generalResponse>>{
        val call=SecuredRetrofitClient.apiInterface.appointmentStatus(token, requestBody)
        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                appointmentStatus.postValue(response.body())
            }
        })
        return appointmentStatus
    }

    fun editFamilyMemberDetailsCall(token : String,familyProfileId:String,requestBody: RequestBody) :MutableLiveData<List<generalResponse>>{
        val call = SecuredRetrofitClient.apiInterface.putFamilyMemberEdit(token,familyProfileId,requestBody)

        call.enqueue(object : Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                editFamilyMeberDetails.postValue(response.body())
            }
        })
        return editFamilyMeberDetails
    }

    fun deleteMemberCall(token:String,familyProfileId: String):MutableLiveData<List<generalResponse>>{
        val call = SecuredRetrofitClient.apiInterface.deleteMember(token,familyProfileId)


        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {

                deleteMember.postValue(response.body())
            }

        })
        return deleteMember
    }

    fun getMemberViewCall(token:String,familyProfileId:String):MutableLiveData<memberViewResponse>{
        val call =SecuredRetrofitClient.apiInterface.viewFamilyMember(token,familyProfileId)

        call.enqueue(object :Callback<memberViewResponse>{
            override fun onFailure(call: Call<memberViewResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<memberViewResponse>,
                response: Response<memberViewResponse>
            ) {
                familyMemberView.postValue(response.body())
            }
        })
        return  familyMemberView
    }



    fun putReschheduleCall(token: String,requesrBody: RequestBody):MutableLiveData<List<generalResponse>>{
        val call=SecuredRetrofitClient.apiInterface.RescheduleAppointment(token,requesrBody)

        call.enqueue(object :Callback<List<generalResponse>>{
            override fun onFailure(call: Call<List<generalResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<generalResponse>>,
                response: Response<List<generalResponse>>
            ) {
                rescheduleAppointment.postValue(response.body())
            }
        })
        return rescheduleAppointment

    }

    fun getNotificationCall(token: String,customerId: String):MutableLiveData<List<notificationResponse>> {
        val call = SecuredRetrofitClient.apiInterface.getNotification(token, customerId)

        call.enqueue(object : Callback<List<notificationResponse>>{
            override fun onFailure(call: Call<List<notificationResponse>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<notificationResponse>>,
                response: Response<List<notificationResponse>>
            ) {
                notification.postValue(response.body())
            }

        })
        return notification
    }

    fun InsuranceListCall(token: String,customerId: String):MutableLiveData<InsuranceList>{
        val call =SecuredRetrofitClient.apiInterface.insuranceList(token, customerId)

        call.enqueue(object :Callback<InsuranceList>{
            override fun onFailure(call: Call<InsuranceList>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<InsuranceList>,
                response: Response<InsuranceList>
            ) {
                insuraceList.postValue(response.body())
            }
        })
        return insuraceList
    }


    companion object {

        private var INSTANCE: UserRepository? = null

        fun getInstance(application: Application): UserRepository = INSTANCE ?: kotlin.run {
            INSTANCE = UserRepository(application = application)
            INSTANCE!!
        }
    }

}