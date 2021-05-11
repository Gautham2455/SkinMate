package com.example.skinmate.ui.home.bookingAppointment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class ScheduleAppointmentFragment :BaseFragment(){

    private val viewModel by viewModels<HomeViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater?.inflate(R.layout.schedule_appointment, container, false)

        val caldendar= view.findViewById<CalendarView>(R.id.date_picker_actions)
        val today = Calendar.getInstance().timeInMillis
        caldendar.setMinDate(today)
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate", Context.MODE_PRIVATE)
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
        viewModel.getBookedAppointments(token,SlectDoctorFragment.doctorID!!,"2021-05/13").observe(requireActivity()){
            var ActualAvalibaleSlots:Array<String>?=null
            Log.v("Book",it[0].responseInformation.toString())
            if(it[0].code==200){
                if(it[0].responseInformation.isEmpty()){

                }
                    //for(availableSlots in resources.getStringArray(R.array.morningSlots))

            }else
            {

            }
        }




        val proocdBtn=view.findViewById<Button>(R.id.proceedBtn)
        proocdBtn.setOnClickListener {

            var day=caldendar.getDate()
            Log.v("DAte",day.toString())
            replace(R.id.fragment_container, AppointmentSummary.newInstance())
        }

        return view
    }

    fun append(arr: Array<Int>, element: Int): Array<Int> {
        val list: MutableList<Int> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    companion object{
        fun newInstance()=ScheduleAppointmentFragment()
    }
}