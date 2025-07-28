package com.example.signinapp

import android.content.Intent
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

class signin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        auth= FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailInput= findViewById<EditText>(R.id.emailInput)
        val passwordInput= findViewById<EditText>(R.id.passwordInput)
        val repasswordInput= findViewById<EditText>(R.id.repasswordInput)
        val signUpButton= findViewById<Button>(R.id.SignupButton)

        signUpButton.setOnClickListener {
             val email= emailInput.text.toString()
            val password= passwordInput.text.toString()
            val repassword= repasswordInput.text.toString()

            if( password!= repassword){
                Toast.makeText(this, "The two password are not the same", Toast.LENGTH_SHORT).show()}

            else {
                createUser(email, password)
                openLogin()

            }

        }

    }
    private fun createUser(email: String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration completed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()}
            }
    }

    private fun openLogin() {
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}