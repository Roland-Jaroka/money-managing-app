package com.example.signinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        auth= FirebaseAuth.getInstance()
        val currentuser= auth.currentUser
        val db = FirebaseFirestore.getInstance()
        if (currentuser!=null) {
            startActivity(Intent(this, dashboard::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailInput= findViewById<EditText>(R.id.emailInput)
        val passwordInput= findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.LoginButton)
        val signUp= findViewById<TextView>(R.id.signUp)
        val forgotPassword= findViewById<TextView>(R.id.forgotPassword)



        loginButton.setOnClickListener {
            val email= emailInput.text.toString()
            val password= passwordInput.text.toString()

            Firebase.analytics.logEvent("Login_Button", null)

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Fill out all the lines", Toast.LENGTH_SHORT).show()
            }
        }

        signUp.setOnClickListener {
            val intent= Intent(this,signin::class.java)
            startActivity(intent)
        }

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }



    }

    private fun loginUser(email:String, password: String) {
        val db = FirebaseFirestore.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {

                    val uid= auth.currentUser?.uid
                    if (uid!=null){
                        db.collection("users").document(uid)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {

                                    Toast.makeText(this, "Sign in was correct", Toast.LENGTH_SHORT).show()

                                    startActivity(Intent(this, dashboard::class.java))
                                    finish()

                                } else {
                                    Toast.makeText(this, "No user Data was found", Toast.LENGTH_SHORT).show()

                                    startActivity(Intent(this, profileMaker::class.java))

                                    finish()
                                }
                            }

                }

            } else { Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()}
            }

    }

}