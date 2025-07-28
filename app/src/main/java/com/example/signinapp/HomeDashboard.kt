package com.example.signinapp

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment: Fragment (R.layout.home_dashboard_layout) {

    private lateinit var auth: FirebaseAuth
    private lateinit var anim: ValueAnimator
    private lateinit var popupWindow: PopupWindow

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()

        val balanceView = view.findViewById<TextView>(R.id.numberView)
        val amountInput = view.findViewById<EditText>(R.id.amountID)
        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val typeInput = view.findViewById<EditText>(R.id.typeInput)
        val addButton = view.findViewById<Button>(R.id.addButton)
        val minusButton =view. findViewById<Button>(R.id.MinusButton)
        val currencyView = view.findViewById<TextView>(R.id.currency)
        val currentSalary = view.findViewById<TextView>(R.id.currentSalary)
        val savingsGoalView = view.findViewById<TextView>(R.id.savingOfThisMonth)
        val spendableView = view.findViewById<TextView>(R.id.spendableView)
        val salaryDateView= view.findViewById<TextView>(R.id.salaryDateView)
        val remainingDaysView = view.findViewById<TextView>(R.id.remainingDaysView)
        val db = FirebaseFirestore.getInstance()


        val currentUser = auth.currentUser
        val uid = currentUser?.uid


        if (currentUser == null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
            return
        }




        // ask data every time the dashboard is opened

        if (uid != null) {

            getFirebaseData { data ->
                currencyView.text = data.currency
                currentSalary.text = "${data.salary} ${currencyView.text}"
                savingsGoalView.text = "${data.savingGoal} ${currencyView.text}"
                salaryDateView.text= "${data.salaryDate}${getString(R.string.th)}"

                // animation of current balance
                animation(0, data.balance, 800, balanceView)

                val dates = getDateInfo()

                remainingDaysView.text="${calculateRemaingDays(dates.currentDay, 
                    dates.daysInMonth,
                    data.salaryDate)} ${getString(R.string.day)}"


                spendableView.text = "${
                    calculateSaving(
                        data.balance,
                        dates.currentDay,
                        dates.daysInMonth,
                        data.salaryDate,
                        data.savingGoal,)
                } ${currencyView.text}"

                 // log
                println(
                    "Calculation result: ${data.balance}, ${dates.currentDay},${dates.daysInMonth}, ${data.savingGoal}, ${
                        calculateSaving(
                            data.balance,
                            dates.currentDay,
                            dates.daysInMonth,
                            data.salaryDate,
                            data.savingGoal
                        )
                    }"
                )

            }
        }

        addButton.setOnClickListener {
              // update map for updating firestore
            val updates = mutableMapOf<String, Any>()

            // calculating the new balance after a plus transaction was made
            if (amountInput.text.isNotEmpty()) {
                val result = addItems(
                    balanceView.text.toString().toInt(),
                    amountInput.text.toString().toInt()
                )


                val currentBalance = balanceView.text.toString().toInt()

                animation(currentBalance, result, 800, balanceView)


                //calculates how much can the user spend to reach his/her goal after a trasaction
                getFirebaseData { data ->

                    val dates = getDateInfo()

                    spendableView.text = "${
                        calculateSaving(
                            result,
                            dates.currentDay,
                            dates.daysInMonth,
                            data.salaryDate,
                            data.savingGoal
                        )
                    } ${currencyView.text}"

                }

               // updates balance after a transaction

                db.collection("users").document(uid!!).update("balance", result)

                // stores the things in the update map so it can update everything at once

                updates["ts_amount"] = amountInput.text.toString().toInt()

                if (nameInput.text.isNotEmpty()) {

                    updates["ts_name"]= nameInput.text.toString()

                } else updates["ts_name"]= "not given"

                if (typeInput.text.isNotEmpty()) {

                    updates["ts_type"]= typeInput.text.toString()

                } else updates["ts_type"]= "not given"

                // stores the time and date of transaction
                val dates= getDateInfo()
                val time= getCurrentTime()
                  updates["ts_date"]=  "${dates.currentDay}.${dates.currentMonth}.${dates.currentYear}" +
                          "/${time.hour}:${time.minute}:${time.second} sec"

                updates["timestamp"]= FieldValue.serverTimestamp()
            }


                 // updates the firestore transaction collections
            if (updates.isNotEmpty()) {
                db.collection("users")
                    .document(uid.toString())
                    .collection("transactions")
                    .add(updates)
                    .addOnSuccessListener {
                        nameInput.text.clear()
                        typeInput.text.clear()
                        amountInput.text.clear()
                    }
            }

        }

        minusButton.setOnClickListener {

            // update map for updating firestore
            val updates = mutableMapOf<String, Any>()

            // calculate transaction after a minus item was made
            if (amountInput.text.isNotEmpty()) {

                val result = removeAmount(amountInput.text.toString().toInt())

                val currentBalance = balanceView.text.toString().toInt()

                animation(currentBalance, result, 800, balanceView)

                getFirebaseData { data ->

                    val dates = getDateInfo()

                    spendableView.text = "${
                        calculateSaving(
                            result,
                            dates.currentDay,
                            dates.daysInMonth,
                            data.salaryDate,
                            data.savingGoal
                        )
                    } ${currencyView.text}"

                }

                // updates balance after a transaction
                db.collection("users").document(uid!!).update("balance", result)


                  // stores the things in the update map so it can update everything at once
                updates["ts_amount"] = 0 - amountInput.text.toString().toInt()

                if (nameInput.text.isNotEmpty()) {

                    updates["ts_name"]= nameInput.text.toString()
                } else updates["ts_name"]= ""

                if (typeInput.text.isNotEmpty()) {

                    updates["ts_type"]= nameInput.text.toString()

                } else updates["ts_type"]= ""

                // store the time and date of transaction
                val dates= getDateInfo()
                val time= getCurrentTime()
                updates["ts_date"]=  "${dates.currentDay}.${dates.currentMonth}.${dates.currentYear}" +
                        "/${time.hour}:${time.minute}:${time.second} sec"

                updates["timestamp"]= FieldValue.serverTimestamp()

            }

            if (updates.isNotEmpty()) {
                db.collection("users")
                    .document(uid.toString())
                    .collection("transactions")
                    .add(updates)
                    .addOnSuccessListener {

                        amountInput.text.clear()
                        nameInput.text.clear()
                        typeInput.text.clear()

                    }
            }


        }



    }

    fun addItems(oldBalance: Int, newItem: Int): Int {
        val equals = oldBalance + newItem
        return equals
    }

    fun removeAmount(newItem: Int): Int {
        val balanceView = view?.findViewById<TextView>(R.id.numberView)
        val equals = balanceView?.text.toString().toInt() - newItem
        return equals
    }

    }