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
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.bookedAppointmentResponse
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.OnClickInterface
import com.example.skinmate.utils.OnClickInterface_
import kotlinx.android.synthetic.main.schedule_appointment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ScheduleAppointmentFragment :BaseFragment(),OnClickInterface, OnClickInterface_ {

    private val viewModel by viewModels<HomeViewModel>()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater?.inflate(R.layout.schedule_appointment, container, false)
        setTitleWithBackButton("Setect Date and Time")
        HomeActivity.bottomNavigationView.visibility = View.GONE
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ScheduleAppointmentFragment.appointmentDate =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            r_appointmentDate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        }

        val caldendar= view.findViewById<CalendarView>(R.id.date_picker_actions)
        val rv_morningSlots=view.findViewById<RecyclerView>(R.id.morning_slots)
        val rv_afternoonSlots=view.findViewById<RecyclerView>(R.id.afternoon_slots)
        val today = Calendar.getInstance().timeInMillis
        caldendar.setMinDate(today)
        var selectedDate:String?=null
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences(
            "SkinMate",
            Context.MODE_PRIVATE
        )
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN, "none")

        viewModel.getBookedAppointments(token, SlectDoctorFragment.doctorID!!,appointmentDate!! ).observe(
            requireActivity()
        ){
            appointmentinfo=it
            Log.v("Book", it[0].responseInformation.toString())

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
        caldendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            date = caldendar.getDate()
            val month=month+1
            r_appointmentDate="$year/$month/$dayOfMonth"
            appointmentDate="$year/$month/$dayOfMonth"
            Log.v("Date","$year/$month/$dayOfMonth")
            viewModel.getBookedAppointments(token, SlectDoctorFragment.doctorID!!,appointmentDate!! ).observe(
                requireActivity()
            ){
                appointmentinfo=it
                Log.v("Book", it[0].responseInformation.toString())

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

        })


        val proocdBtn=view.findViewById<Button>(R.id.proceedBtn)
        proocdBtn.setOnClickListener {
            appointmentSlots.removeAt(0)
            Log.v("Slos", appointmentSlots.toString())

            replace(R.id.fragment_container, AppointmentSummary.newInstance())
        }

        return view
    }

    override fun getViewPosition(position: Int) {
        Log.v("Btn","mor $position")
        if(appointmentSlots.contains(MorningSlots.get(position)))
            appointmentSlots.remove(MorningSlots.get(position))
        else
            appointmentSlots!!.add(MorningSlots.get(position))
    }

    override fun getViewPosition_(position: Int) {
        Log.v("Btn","aft $position")
        if(appointmentSlots.contains(AfternoonSlots.get(position)))
            appointmentSlots.remove(AfternoonSlots.get(position))
        else
            appointmentSlots!!.add(AfternoonSlots.get(position))
    }

    companion object{
        var appointmentDate:String?=null
        var r_appointmentDate:String?=null
        var appointmentSlots= mutableListOf<String>("none")
        var appointmentinfo:bookedAppointmentResponse?=null
        fun newInstance()=ScheduleAppointmentFragment()
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
    }
}