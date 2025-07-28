package com.example.signinapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.material3.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class profileSettings: Fragment(R.layout.fragment_profile) {

    val auth= FirebaseAuth.getInstance()
     val db = FirebaseFirestore.getInstance()
    val uid= auth.currentUser?.uid

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val monthlySalaryInput = view.findViewById<EditText>(R.id.monthlySalaryInput)
        val setDateInput = view.findViewById<EditText>(R.id.setDateInput)
        val savingGoalsInput = view.findViewById<EditText>(R.id.savingGoalsInput)
        val notifyMe = view.findViewById<SwitchCompat>(R.id.notifyMeButton)
        val savingAllButton = view.findViewById<AppCompatButton>(R.id.saveAllButton)
        val resetAllButton = view.findViewById<AppCompatButton>(R.id.resetAllButton)


        savingAllButton.setOnClickListener {

            val updates = mutableMapOf<String, Any>()

            if (monthlySalaryInput.text.isNotEmpty()) {
                updates["salary"] = monthlySalaryInput.text.toString().toInt()
            }

            if (setDateInput.text.isNotEmpty()) {

                if (setDateInput.text.toString().toInt() !in 1..31 ) {

                    Toast.makeText(requireContext(), "Invalid date number", Toast.LENGTH_SHORT).show()

                    return@setOnClickListener

                } else updates["salaryDate"] = setDateInput.text.toString().toInt()
            }

            if (savingGoalsInput.text.isNotEmpty()) {
                updates ["savingGoal"] = savingGoalsInput.text.toString().toInt()
            }

            if (updates.isNotEmpty()) {
                db.collection("users").document(uid.toString()).update(updates)
                    .addOnSuccessListener {
                        if ("salary" in updates) monthlySalaryInput.text.clear()
                        if ("salaryDate" in updates) setDateInput.text.clear()
                        if ("savingGoal" in updates) savingGoalsInput.text.clear()

                        Toast.makeText(requireContext(), "All data has been saved", Toast.LENGTH_SHORT).show()
                    }
            }
            else Toast.makeText(requireContext(), "No saveable input was found", Toast.LENGTH_SHORT).show()


        }

        resetAllButton.setOnClickListener {

            monthlySalaryInput.text.clear()
            setDateInput.text.clear()
            savingGoalsInput.text.clear()
        }
    }
}