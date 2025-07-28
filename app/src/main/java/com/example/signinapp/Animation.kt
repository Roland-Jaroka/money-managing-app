package com.example.signinapp

import android.animation.ValueAnimator
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

private var auth = FirebaseAuth.getInstance()


fun animation(fromNumber: Int, toNumber: Int, animDuration: Long, view: View) {
    val anim = ValueAnimator.ofInt(fromNumber,toNumber)
    anim.duration= animDuration
    anim.addUpdateListener {
        (view as TextView).text= "${it.animatedValue}"
    }
    anim.start()
}