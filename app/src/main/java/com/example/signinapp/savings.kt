package com.example.signinapp

import android.annotation.SuppressLint


fun calculateSaving (balance: Int, todaysDate: Int, daysOfMonth: Int, salaryDateInput: Int, savingGoal: Int): Int{

    var salaryDate= salaryDateInput

    if (daysOfMonth < salaryDate) salaryDate = daysOfMonth

    var remaningDays = salaryDate - todaysDate

          if (salaryDate < todaysDate ) {
              remaningDays=  (daysOfMonth-todaysDate) + salaryDate
          }

    val spendableAmount= balance - savingGoal

    if (remaningDays<=0) {

        return spendableAmount
    }

    return spendableAmount / remaningDays
}


fun calculateRemaingDays(todaysDate: Int, daysOfMonth: Int, salaryDateInput: Int): Int {

    var salaryDate= salaryDateInput

    if (daysOfMonth < salaryDate){

        salaryDate = daysOfMonth
    }

    var remaningDays = salaryDate - todaysDate

    if (salaryDate < todaysDate ) {
        remaningDays=  (daysOfMonth-todaysDate) + salaryDate
    }
    return remaningDays
}

