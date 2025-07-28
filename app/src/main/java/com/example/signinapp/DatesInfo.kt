package com.example.signinapp

import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.YearMonth


data class DatesInfo(
    val currentDay: Int,
    val currentMonth: Month,
    val currentYear: Int,
    val daysInMonth: Int,
)

data class TimeInfo(
    val hour: Int,
    val minute: Int,
    val second: Int
)


fun getDateInfo(): DatesInfo {

    val currentDate = LocalDate.now()
    val currentDay= currentDate.dayOfMonth
    val currentMonth= currentDate.month
    val currentYear= currentDate.year
    val daysInMonth= YearMonth.of(currentYear,currentMonth).lengthOfMonth()

    return DatesInfo(currentDay,currentMonth, currentYear, daysInMonth)
}

fun getCurrentTime() : TimeInfo {

    val currentTime= LocalTime.now()
    val hour= currentTime.hour
    val minute= currentTime.minute
    val second= currentTime.second

    return TimeInfo(hour, minute, second)

}
