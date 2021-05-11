package com.example.skinmate.ui.home.bookingAppointment

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
import android.widget.CalendarView.OnDateChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.OnClickInterface
import com.example.skinmate.utils.OnClickInterface_


class ScheduleAppointmentFragment :BaseFragment(),OnClickInterface, OnClickInterface_ {

    private val viewModel by viewModels<HomeViewModel>()
    var MorningSlots= mutableListOf<String>(
        "09:00",
        "09:10",
        "09:20",
        "09:30",
        "09:40",
        "09:50",
        "10:00",
        "10:10",
        "10:20",
        "10:30",
        "10:40",
        "10:50",
        "11:00",
        "11:10",
        "11:20",
        "11:30",
        "11:40",
        "11:50"
    )
    var AfternoonSlots= mutableListOf<String>(
        "01:00",
        "01:10",
        "01:20",
        "01:30",
        "01:40",
        "01:50",
        "02:00",
        "02:10",
        "02:20",
        "02:30",
        "02:40",
        "02:50",
        "03:00",
        "03:10",
        "03:20",
        "03:30",
        "03:40",
        "03:50",
        "04:00",
        "04:10",
        "04:20",
        "04:30",
        "04:40",
        "04:50",
        "05:00"
    )

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
        var selectedDate:String?=null
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences(
            "SkinMate",
            Context.MODE_PRIVATE
        )
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN, "none")
        viewModel.getBookedAppointments(token, SlectDoctorFragment.doctorID!!, "2021-05/13").observe(
            requireActivity()
        ){

            Log.v("Book", it[0].responseInformation.toString())

            val rv_morningSlots=view.findViewById<RecyclerView>(R.id.morning_slots)
            val rv_afternoonSlots=view.findViewById<RecyclerView>(R.id.afternoon_slots)

            if(it[0].code==200) {
                if (it[0].responseInformation.isEmpty()) {

                } else {

                    for (bookedSlots in it[0].responseInformation) {
                        if(MorningSlots.contains(bookedSlots)){
                            MorningSlots.remove(bookedSlots)
                        }
                        if(AfternoonSlots.contains(bookedSlots))
                            AfternoonSlots.remove(bookedSlots)
                    }
                }
            }else   {

            }
            val morningSlots_adapter=MorningTimeSlotAdapter(MorningSlots,requireContext(),this)
            rv_morningSlots.layoutManager = GridLayoutManager(context, 3)
            val afternoonSlots_adapter=AfternoonTimeSlotAdapter(AfternoonSlots,requireContext(),this)
            rv_morningSlots.setAdapter(morningSlots_adapter)
            rv_afternoonSlots.layoutManager = GridLayoutManager(context, 3)
            rv_afternoonSlots.setAdapter(afternoonSlots_adapter)
            Log.v("Slots", AfternoonSlots.toString())
        }

        var date = caldendar.getDate();


        val proocdBtn=view.findViewById<Button>(R.id.proceedBtn)
        proocdBtn.setOnClickListener {

            //var day=caldendar.setOnDateChangeListener()
            caldendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
                if (caldendar.getDate() !== date) {
                    date = caldendar.getDate()
                    selectedDate="$year/$month/$dayOfMonth"
                }
            })
            appointmentDate=selectedDate

            replace(R.id.fragment_container, AppointmentSummary.newInstance())
        }

        return view
    }

    fun remove(arr: Array<String>, index: Int): Array<String> {
        if (index < 0 || index >= arr.size) {
            return arr
        }

        val result = arr.toMutableList()
        result.removeAt(index)
        return result.toTypedArray()
    }

    companion object{
        var appointmentDate:String?=null
        var appointmentSlots:MutableList<String>?=null
        fun newInstance()=ScheduleAppointmentFragment()
    }

    override fun getViewPosition(position: Int) {
        Log.v("Btn",position.toString())
        appointmentSlots?.add("aa")
    }

    override fun getViewPosition_(position: Int) {
        Log.v("Btn",position.toString())
    }
}