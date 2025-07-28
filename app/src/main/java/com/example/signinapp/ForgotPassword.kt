package com.example.signinapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth= FirebaseAuth.getInstance()

        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }


        val emailInput= findViewById<EditText>(R.id.emailInput)
        val resetPasswordButton= findViewById<Button>(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val email= emailInput.text.toString()
            if (email.isNotEmpty()) {

                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Reset password has been sent", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "An error has occured", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(this, "Type a valid email adress", Toast.LENGTH_SHORT).show()
            }
        }


    }
}