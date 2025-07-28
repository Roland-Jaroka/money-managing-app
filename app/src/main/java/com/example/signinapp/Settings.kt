package com.example.signinapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class SettingsFragment: Fragment (R.layout.setting_layout) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth= FirebaseAuth.getInstance()
        val logoutButton= view.findViewById<Button>(R.id.logoutButton)
        val languagePicker= view.findViewById<Spinner>(R.id.languagePicker)
        val saveButton= view.findViewById<Button>(R.id.saveButton)

        val languages = arrayOf("English", "Japanese", "Hungarian")
        val languageCodeMap= mapOf(
            "en" to "English",
            "ja" to "Japanese",
            "hu" to "Hungarian"
        )

        val adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        languagePicker.adapter= adapter


        val currentLanguageCode= Locale.getDefault().language
        val currentLanguageName= languageCodeMap[currentLanguageCode]?: "English"
        val selectedIntex = languages.indexOf(currentLanguageName)
        languagePicker.setSelection(selectedIntex)

        saveButton.setOnClickListener {
            when (languagePicker.selectedItem) {
                "Japanese" -> setAppLanguage("ja")
                "English" -> setAppLanguage("en")
                "Hungarian" -> setAppLanguage("hu")
            }


        }


        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
    }


}