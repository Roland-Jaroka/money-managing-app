package com.example.signinapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragments(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val fragmentTransaction= fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragment_container,fragment)
    fragmentTransaction.commit()
}