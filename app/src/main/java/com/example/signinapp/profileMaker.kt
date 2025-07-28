package com.example.signinapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class profileMaker : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_maker)

        val db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentBalanceInput = findViewById<EditText>(R.id.currentBalanceInput)
        val salaryInput= findViewById<EditText>(R.id.salaryInput)
        val savingInput= findViewById<EditText>(R.id.savingInput)
        val salaryDateInput = findViewById<EditText>(R.id.salaryDate)
        val currencyPicker= findViewById<Spinner>(R.id.currencyPicker)
        val startButton= findViewById<Button>(R.id.startButton)
        val currentUser= auth.currentUser
        val uid= currentUser?.uid

        val currencies= arrayOf("USD", "JPY", "EUR", "HUF")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        currencyPicker.adapter= adapter


        startButton.setOnClickListener {
            val userData= hashMapOf(
                "balance" to currentBalanceInput.text.toString().toLongOrNull(),
                "salary" to salaryInput.text.toString().toLongOrNull(),
                "savingGoal" to savingInput.text.toString().toLongOrNull(),
                "currency" to currencyPicker.selectedItem.toString(),
                "salaryDate" to salaryDateInput.text.toString().toLongOrNull()
            )
            if(
                currentBalanceInput.text.isNotEmpty() &&
                salaryInput.text.isNotEmpty() &&
                savingInput.text.isNotEmpty() &&
                salaryDateInput.text.isNotEmpty()
            ) {
                db.collection("users").document(uid.toString()).set(userData)
                   startActivity(Intent(this, dashboard::class.java)) }

            }






        }



    }