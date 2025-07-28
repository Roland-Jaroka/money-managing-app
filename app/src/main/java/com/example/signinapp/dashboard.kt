package com.example.signinapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import java.util.Locale
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class dashboard : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.fragment_home)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard_navbar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navBar= findViewById<BottomNavigationView>(R.id.bottomNav)

        navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {replaceFragment(HomeFragment())
                    true }
                R.id.nav_history -> {replaceFragment(History())
                true}

                R.id.nav_profile-> {replaceFragment(profileSettings())
                true}
                R.id.nav_settings -> {replaceFragment(SettingsFragment())
                    true}
                else -> false
            }

        }

        replaceFragment(HomeFragment())


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}