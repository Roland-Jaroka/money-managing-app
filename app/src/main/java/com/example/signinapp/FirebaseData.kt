package com.example.signinapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.security.auth.callback.Callback

data class FirebaseData(
   val balance: Int,
    val currency: String,
    val salary : Int,
    val savingGoal: Int,
    val salaryDate: Int

)



fun getFirebaseData(callback: (FirebaseData)-> Unit){
    val auth= FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val uid= auth.currentUser?.uid

    db.collection("users").document(uid.toString()).get()
        .addOnSuccessListener { document ->
            val balance = document.getLong("balance")?.toInt()?:0
            val currency = document.getString("currency")?: ""
            val salary = document.getLong("salary")?.toInt()?:0
            val salaryDate= document.getLong("salaryDate")?.toInt()?:0
            val savingGoal= document.getLong("savingGoal")?.toInt()?:0

        val data = FirebaseData(balance, currency, salary, savingGoal, salaryDate)

            callback(data)

        }
}
